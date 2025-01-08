# Step-1

## 목표 
1. Terraform을 사용하여 vSphere 환경에서 가상머신을 생성
2. Ansible을 사용하여 생성된 가상머신에서 kubespray를 사용하여 k8s 클러스터 구축
3. Github Action을 사용해서 1,2번 내용을 한꺼번에 실행하도록 설정

## 1. Terraform을 사용하여 vSphere 환경에서 가상머신을 생성

* 1-1. main.tf 에는 vCenter 접속 정보 및 데이터 소스를 정의 해줍니다.
* 1-2. 01_create_vm.tf 에는 가상머신을 생성하기 위해, 앞서 정의해둔 데이터 소스를 사용하여 가상머신의 스펙, IP 등을 정의 해줍니다.

## 2. Ansible을 사용하여 생성된 가상머신에서 kubespray를 사용하여 k8s 클러스터 구축

* 2-1. inventory.ini 에 그룹 별 호스트 정의
    * package 그룹 => kubespray를 사용하기 전 필요한 패키지 및 작업 진행 하기 위해 모든 호스트를 넣어줍니다.
    * master 그룹 => kubespray 실행은 master1 서버에서만 진행하기 때문에, master1 호스트만 넣어줍니다.

* 2-2. setup.yml 작성
    * 호스트 그룹 마다 Playbook이 실행 될 역할(role)과 태그 정의

* 2-3. 역할(role) 별 태스크 구성
* package 태스크:
    * kubespray 설치 작업을 하기 전 SSH 접속을 Password로 하기 위해 sshpass 패키지를 설치 해줍니다.
    * 또한, 패키지 설치 시에 dpkg 잠금을 해결 하기 위해 unattended-upgrades 서비스와 apt-daily.timer를 종료 시켜줍니다.

* master 태스크:
    * kubespray 실행하기 위한 사전 패키지 설치
    * kubespray 용 inventory.ini 설정
    * Container Runtime 설정
    * kubespray 실행