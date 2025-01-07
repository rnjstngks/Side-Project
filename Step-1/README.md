# 1. 환경 구성

## 목표 
* 1. Terraform을 사용하여 vSphere 환경에서 인프라 환경 구성
* 2. kubespray를 사용하여 k8s 클러스터 구축

## i. Terraform을 사용하여 vSphere 환경에서 인프라 환경 구성

먼저  main.tf 에 vCenter 접속 정보 및 데이터 소스를 정의 해줍니다.

그 후, 