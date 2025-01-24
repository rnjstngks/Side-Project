# Step-3

## 목표 
* Gradle build를 진행
* Docker 빌드를 통해 이미지 생성
* 생성된 이미지로 POD 로 배포
* 위 모든 과정을 Github Action 에서 실행

## 1. Gradle build 진행

* Github Action 파일에서 Build 진행 [step-3](/.github/workflows/step-3.yml)

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

## 2. Docker 이미지 빌드

* Github Action 파일에서 Image Build 진행 [step-3](/.github/workflows/step-3.yml)

```sh
Docker_image_build:
    runs-on: self-hosted
    needs: gradle_build
```


```sh
- name: Permission change
  run: sudo chmod 666 /var/run/docker.sock
 ```

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
