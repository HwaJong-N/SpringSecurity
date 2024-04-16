# SpringSecurity


> 스프링부트 3.0 이상 버전은 해당 <a href="https://github.com/HwaJong-N/Spring-Security-JWT-OAuth2.0">Repository</a>에서 확인할 수 있습니다.


<br>


## 회원가입

![Animation_회원가입](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/2c91e0c4-bf30-4f56-acfb-058b1b07f025)


회원가입 시 ID, 비밀번호, 이름, 이메일을 입력합니다.


<br>
<br>


## 로그인

![Animation_로그인](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/6daf80e7-08e3-46ff-8c04-8c9b0b4edf54)


로그인 시 ID 와 비밀번호를 입력하고, 로그인이 성공한다면 alert 창과 함께 로그인 성공 페이지로 이동합니다.

로그인 성공을 확인하기 위해 페이지 접근 권한을 체크하지 않았습니다.


<br>
<br>


## OAUth 2.0 최초 로그인 ( 회원가입 )

![Animation_oauth회원가입](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/c3e785ff-4d6b-441b-9581-10e8569a73e3)


OAuth 2.0 을 통한 최초 로그인 시 회원가입 페이지로 이동합니다.

이때 소셜로부터 받은 이메일 정보는 자동으로 form 에 들어가게 되고, readOnly 로 설정되어 수정할 수 없습니다.


<br>
<br>



## OAuth 2.0 로그인

![Animation_oauth로그인](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/e0dd0730-05a9-433e-9add-9e1ceac586ee)


OAuth 2.0 로그인 시 로그인이 성공한다면 로그인 성공 페이지로 이동합니다.


<br>
<br>


## Postman 로그인

![Animation_postman 로그인](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/901ef711-f1b9-417c-ad26-28df2b4353a1)



Postman 을 이용한 로그인 시도입니다. 로그인이 성공하면 Response Header 에 Access Token 과 Refresh Token 을 전달받게 됩니다.


<br>
<br>


## Postman User 가 User 요청

![Animation_postman _user 요청](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/91010f5a-f76c-4566-85d9-0957ed5d541b)


전달받은 Access Token 으로 ```/user``` 요청을 하게 되면 user 입니다 라는 메시지가 출력됩니다.


<br>
<br>


## Postman User 가 Admin 요청

![Animation_postman _admin 요청](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/8e975dc3-6680-4a57-b1cc-58f318846ef0)


User 가 ```/admin``` 요청을 하게 되면 접근 권한이 없다는 메세지가 출력됩니다.


<br>
<br>

## Postman Admin 이 Admin 요청


![Animation_postman _admin](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/0ce53dcb-5be7-40ba-8930-f11ef6fbe3a9)


admin 권한을 가진 아이디로 로그인 후 ```/admin``` 요청을 하게 되면 정상적으로 접근됩니다.


<br>
<br>



## Postman Access Token 재발급

![Animation_postman _renew](https://github.com/HwaJong-N/SpringSecurity/assets/112313795/601e41b3-215f-4ad0-8f23-f7ffe8f306fa)


Access Token 이 만료되면 ```/renew``` 를 통해 Refresh Token 을 전달하여 새롭게 Access Token 을 받을 수 있습니다.
