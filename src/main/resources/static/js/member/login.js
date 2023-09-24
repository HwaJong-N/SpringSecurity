const loginUser = document.querySelector("#login-user");

// 버튼 클릭 이벤트 감지
loginUser.addEventListener("click", () => {

    // 태그의 id 를 이용해 입력된 값들을 불러와 객체 생성
    const user = {
        id: document.querySelector("#id").value,
        password: document.querySelector("#password").value,
    }

    // RestAPI 호출
    fetch("/login", {
        method: "post",
        headers: {"Content-Type": "application/json"},  // body 에 담긴 데이터 타입을 명시
        body: JSON.stringify(user)  // 생성한 객체를 JSON 형식으로 변경
    }).then(response => {
        if (response.status === 200) {
            location.href = "/loginSuccess";
        } else {
            alert("로그인에 실패하였습니다.");
            location.reload();
        }
    })
});