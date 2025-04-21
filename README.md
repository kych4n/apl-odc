# Self-sovereign and Secure Filtered Data Sharing

## 1. 인증 과정

### UDC의 인증 요청 과정

![image](https://github.com/user-attachments/assets/f4bf032e-417e-408c-b655-9141536b869f)

<p>UDC가 인증 정보를 가지고 있고, ODC는 접근이 허용된 UDC의 인증 정보를 가지고 있다.

UDC는 ODC에 인증 요청을 하기 위해 인증 정보를 조회한다. 인증 정보에는 username, password, seed가 포함된다. seed를 이용하여 TOTP를 생성하고, ODC에 username, password, TOTP를 전송한다.</p>

&nbsp;

### ODC의 인증 요청 처리 과정

![image 1](https://github.com/user-attachments/assets/08ec67d6-63ac-41d6-8545-32eb6ee1520b)

ODC는 접근을 허용하는 UDC의 정보를 저장하는 저장소(현재는 데이터베이스)에서 1차로 username과 password로 UDC의 정보가 존재하는지 확인하고, 조회한다. UDC 정보가 존재한다면, 2차로 seed를 이용하여 전달받은 TOTP를 검증한다.

TOTP 검증까지 완료되었다면, userId(username과는 구별됨)와 ACP를 포함한 JWT 토큰을 생성하고, UDC에 전송하여 이후 요청 시 JWT 토큰을 포함하여 요청을 보내도록 한다.

&nbsp;

### UDC의 인증 요청 결과

![image 2](https://github.com/user-attachments/assets/be5be97a-0e3b-4302-b65f-37a61620d63f)

&nbsp;

## 2. ODC 애트리뷰트 조회

### ODC의 애트리뷰트 제공 과정

![image 3](https://github.com/user-attachments/assets/6c7604e5-5d53-4b8f-b74e-ceef8b3e1674)

UDC가 이전에 발급받은 JWT 토큰과 함께 요청을 보내면, ODC는 ACP 권한이 있는지 확인하고, 있다면 자신이 가지고 있는 데이터에 포함된 애트리뷰트들을 응답으로 제공한다.

&nbsp;

### ODC의 애트리뷰트 조회 요청 결과

![image 4](https://github.com/user-attachments/assets/df98c723-dbca-45dc-8043-5a9bb2d41676)

&nbsp;

## 3. 필터링

### ODC의 필터링 요청 처리 과정

![image 5](https://github.com/user-attachments/assets/81bfa676-0afe-4cf0-bf74-8bff337390d0)

UDC는 ACP 정보가 포함된 JWT 토큰, Rule 파일과 Frame 파일들이 존재하는 Url, ODC의 애트리뷰트와 자신이 매핑하고 싶은 애트리뷰트를 담은 정보를 포함하여 ODC에게 요청한다.

ODC는 전달받은 Url에서 Rule 파일과 Frame 파일을 다운로드한다. 프로젝트에 변경사항이 발생했으므로, 빌드 후에 필터링을 실행한다. 필터링된 데이터는 암호화 과정을 거쳐 서버에 업로드되고, 서버에서의 Url을 UDC에게 반환한다. Url에는 제한 시간이 있어, 이 시간이 지나면 더이상 접근할 수 없다.

&nbsp;

### 필터링 결과

![스크린샷_2025-02-19_230809](https://github.com/user-attachments/assets/d8502ddc-6336-4b99-8058-32689d83cb4e)

&nbsp;

### 필터링 요청 결과

![스크린샷_2025-02-20_151350](https://github.com/user-attachments/assets/09051a03-be5d-4cac-b078-9e423eba4e8f)

&nbsp;

### 암호화된 데이터 업로드

![암호화된 데이터 업로드](fdee4b27-6018-40f1-9ba0-293c2bbeafa7.png)

&nbsp;

## UDC의 암호화 데이터 다운로드 및 복호화 과정

![image 6](https://github.com/user-attachments/assets/3808a955-796e-4a3e-ae9d-1f7424ac3e68)

UDC는 필터링과 암호화를 거친 데이터의 Url을 전달받아 암호화된 데이터를 다운로드한다. 복호화 키를 생성하고, 복호화를 거쳐 모델 학습에 이용할 수 있는 데이터로 변환된다. 

&nbsp;

### 복호화 결과

![복호화_성공](https://github.com/user-attachments/assets/943d06a3-2088-4b14-af69-a2d900e033f8)

&nbsp;

## 4. 데이터 사용 및 종료

### UDC의 데이터 사용 및 종료 과정

![image 7](https://github.com/user-attachments/assets/d917da37-f967-4e8c-bb8b-18f2d38646ae)

UDC는 암호화된 데이터를 모델 학습에 사용하고, 사용이 완료되었으면, UDC에 있는 데이터를 제거하고, ODC에 서버에 업로드된 파일을 제거하기 위한 요청을 보낸다.

&nbsp;

### UDC의 데이터 사용 종료 후, ODC에서 파일을 제거하는 과정

![image 8](https://github.com/user-attachments/assets/0e6bf16b-0338-4861-9505-e55ca5375b56)

ODC는 서버에 업로드한 파일을 제거한다.

&nbsp;

### ODC가 업로드한 파일 제거 확인

![image 9](https://github.com/user-attachments/assets/10b29a9c-0e99-4b55-b634-841825df5acc)

