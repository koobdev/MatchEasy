window.onload = function(){
    userInfo();
}

function userInfo(){
    if(storage.get("token") != null){

    }
    let xhr = new XMLHttpRequest();
    let token = storage.get("token");
    let url = "http://localhost:8080/";

    // me함수를 사용한 로그인 사용자 정보(name, id) 찍어주기
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.responseType = 'json';
    xhr.send();
    // xhr.onreadystatechange = function () {
    //     if(xhr.readyState == 4 && xhr.status == 200) {
    //         accessToken = xhr.response.accessToken;
    //
    //         // token이 refresh 되면 storage에 set해줌
    //         if(accessToken != null){
    //             storage.set("token", accessToken);
    //         }else if(accessToken == null){
    //             UIkit.modal.alert('연결 시간이 초과되었습니다. 다시 로그인해주세요').then(function () {
    //                 logout();
    //             });
    //         }
    //     }
    // };
}

// 로그아웃
function logout(){
    storage.removeAll();
    window.location.href = "/";
}