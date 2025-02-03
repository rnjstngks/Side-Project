# Step-3

## 목표 
* Gradle build를 진행
* Docker 빌드를 통해 이미지 생성
* was.yaml 파일의 내용 중 이미지 태그 변경하여 POD내의 컨테이너 이미지 변경
* 위 모든 과정을 Github Action 에서 실행

## 1. Gradle build 진행

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

## 2. Docker 이미지 빌드

* Github Action 파일에서 Image Build 진행 [step-3.yml](/.github/workflows/step-3.yml)

앞서 gradle build 작업이 성공적으로 완료가 되면 다음 작업이 실행 되도록 "needs: gradle_build" 를 추가 해주었습니다.

```sh
Docker_image_build:
    runs-on: self-hosted
    needs: gradle_build
```

docker build 를 하기 전, docker.sock의 권한을 조정해줍니다.

해당 권한을 조정해주지 않으면 docker build 명령을 사용할 떄, Permission 오류가 나오게 됩니다.

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



```sh
- name: Docker Hub login
  uses: docker/login-action@v2
  with:
    username: ${{ secrets.DOCKER_USERNAME }}
    password: ${{ secrets.DOCKER_PASSWORD }}
```

```sh
- name: Docker Push
  run: docker push rnjstngks/side-project-was:${{ github.run_number }}
```
