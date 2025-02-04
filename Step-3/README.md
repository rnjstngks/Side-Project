# Step-3

## 목표 
* Gradle build를 진행
* Docker 빌드를 통해 이미지 생성
* was.yaml 파일의 내용 중 이미지 태그 변경하여 POD내의 컨테이너 이미지 변경
* ArgoCD를 사용하여 변경된 내용으로 자동 배포

## 1. ArgoCD 설치 및 Application 설정

* 1-1. Helm 차트 설치

```sh
curl https://baltocdn.com/helm/signing.asc | gpg --dearmor | sudo tee /usr/share/keyrings/helm.gpg > /dev/null
sudo apt-get install apt-transport-https --yes
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/helm.gpg] https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
sudo apt-get update
sudo apt-get install helm
```

* 1-2. ArgoCD 설치

```sh
helm repo add argo https://argoproj.github.io/argo-helm
helm install argocd argo/argo-cd
```

* 1-3. Application 추가

was, web.yaml 파일이 있는 Github Repository를 등록해주면 자동으로 ArgoCD가 쿠버네티스 클러스터에서 배포를 진행해줍니다.


## 2. Gradle build 진행

* Github Action 파일에서 Build 진행 [step-3.yml](/.github/workflows/step-3.yml)

working-directory에서 정의해둔 Path에는 개발한 코드들이 있고 해당 Path에서 "./gradlew build" 명령을 통해 빌드를 진행합니다.

```sh
- name: Build with Gradle
  working-directory: /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-2/sbb
  run: ./gradlew build --stacktrace --info
```       

그 후, build 가 정상적으로 완료되면, working-directory에서 정의해둔 경로에 jar 파일이 생성이 되고,

해당 jar 파일을 다른 디렉터리로 복사하여 옮겨줍니다.

```sh
- name: Copy jar file
  working-directory: /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-2/sbb/build/libs
  run: cp *-SNAPSHOT.jar /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-3/was/
```

마지막으로 생성된 jar 파일을 Github 리포지터리에 Push 해주면, gradle_build 과정이 끝나게 됩니다.

Github Token은 secrets 변수 처리를 미리 해주어서, Push 해줄 때, Github Token을 사용해서 push를 해주었습니다.

```sh
- name: Push jar file
  working-directory: /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-3/was/
  run: |
    git config --global user.name "github-action[bot]"
    git config --global user.email "github-actions[bot]@users.noreply.github.com"
    git add *.jar
    git commit -m "Upload jar file"
    git push https://x-access-token:${{ secrets.GH_PAT }}@github.com/rnjstngks/Side-Project.git main
```

<br>

## 3. Docker 이미지 빌드

* Github Action 파일에서 Image Build 진행 [step-3.yml](/.github/workflows/step-3.yml)

앞서 gradle build 작업이 성공적으로 완료가 되면 다음 작업이 실행 되도록 "needs: gradle_build" 를 추가 해주었습니다.

```sh
Docker_image_build:
    runs-on: self-hosted
    needs: gradle_build
```

docker build 를 하기 전, docker.sock의 권한을 조정해줍니다.

해당 권한을 조정해주지 않으면 docker build 명령을 사용할 때, Permission 오류가 나오게 됩니다.

```sh
- name: Permission change
  run: sudo chmod 666 /var/run/docker.sock
 ```

이제 working-directory 에서 정의한 Path 에 이미지 빌드에 필요한 Dockerfile 및 jar 파일을 같은 디렉터리에 놓고,

docker build 를 진행해줍니다.

 ```sh
- name: Docker Build
  working-directory: /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-3/was/
  run: docker build -t rnjstngks/side-project-was:${{ github.run_number }} .
 ```

그리고 Docker hub에 이미지를 Push 해주기 위해 Docker hub에 로그인 해줍니다.

```sh
- name: Docker Hub login
  uses: docker/login-action@v2
  with:
    username: ${{ secrets.DOCKER_USERNAME }}
    password: ${{ secrets.DOCKER_PASSWORD }}
```

앞서 빌드한 이미지를 Docker hub에 Push 해줍니다.

Push 해주는 이미지 태그 값은, github action 의 동작 번호로 지정 해줍니다.

```sh
- name: Docker Push
  run: docker push rnjstngks/side-project-was:${{ github.run_number }}
```

새로운 이미지로 컨테이너 이미지를 변경하기 위해 was.yaml 파일의 내용 중, 이미지 태그 부분 sed 명령어를 사용하여 변경해줍니다.

```sh
- name: Change Image Tag
  working-directory: /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-3/
  run: |
    sed -i 's|image: rnjstngks/side-project-was:.*|image: rnjstngks/side-project-was:${{ github.run_number }}|g' was.yaml
```

마지막으로 내용이 변경된 was.yaml 파일을 다시 git push 해줍니다.

```sh      
- name: Change was.yaml file
  working-directory: /mnt/c/Users/Snetsystems/Documents/GitHub/Side-Project/Step-3/
  run: |
    git config --global user.name "github-action[bot]"
    git config --global user.email "github-actions[bot]@users.noreply.github.com"
    git add was.yaml
    git commit -m "Change was.yaml file"
    git push https://x-access-token:${{ secrets.GH_PAT }}@github.com/rnjstngks/Side-Project.git main
```