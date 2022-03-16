function makeBoardContentDiv(content){
    let tbody = "";

    if(content.status === 0){
        tbody += `
                <div class="board-card uk-margin-bottom" onclick="openDetail(`+ content.id +`)">
                    <div class="uk-card uk-card-default">
                        <div class="uk-card-header">
                            <div class="uk-text-right uk-margin-bottom">`;
        tbody += `<button class="uk-button uk-button-small uk-text-nowrap">모집중</button>`;
    }else {
        tbody += `
                <div class="board-card uk-margin-bottom">
                    <div class="uk-card uk-card-default">
                        <div class="uk-card-header">
                            <div class="uk-text-right uk-margin-bottom">`;
        tbody += `<button class="uk-button uk-button-danger uk-button-small uk-text-nowrap">모집완료</button>`;
    }

    tbody += `  </div>
                <div class="uk-width-expand">
                    <p class="uk-text-small">` + content.title + `</p>
                </div>
            </div>
            <div class="uk-card-body">
                <p id="content uk-text-nowrap">` + content.content + `</p>
                <p class="uk-text-small uk-text-nowrap" id="date">` + content.startdate.substr(0, 10) + `~` + content.enddate.substr(0, 10) + `</p>
            </div>
            <div class="uk-card-footer">
                <div class="uk-width-auto">
                    <img th:src="@{/static/image/java.png}">
                    <img th:src="@{/static/image/spring.png}">
                    <img th:src="@{/static/image/react.png}">
                </div>
            </div>
        </div>
    </div>
    `;

    return tbody;
}


function makePositionListDiv(position) {
    let tbody = "";
    tbody += `
            <tr id="`+ position.id +`">
                <td>` + position.position + `</td>
                <td>` + position.content + `</td>`;

    if(position.status === 0){
        tbody += `<td><button class="uk-button-primary uk-button-small uk-text-nowrap" onclick="recruitPosition(this)">지원하기</button></td>`;
    }else {
        tbody += `<td><button class="uk-button uk-button-danger uk-button-small uk-text-nowrap" disabled>모집완료</button></td>`;
    }

    if(position.recruitMessage == null){
        tbody += `<td><input class="uk-input uk-form-width-xlarge uk-form-small" type="text"/></td>`;
    }else {
        tbody += `<td>` + position.recruitMessage + `</td>`;
    }

    tbody += `
            </tr>
            `;

    return tbody;
}

/**
 * 지원자 관리 페이지
 */
function makeRecruitPositionListDiv(position) {
    let tr = `
        <tbody class="uk-text-0_9" >
        <tr id="` + position.id + `" onclick="showSub(` +  position.id + `)" class="uk-background-muted">
            <td>` + position.position + `</td>
            <td>` + position.content + `</td>`;

    if(position.status === 0){
        tr += `<td>모집중</td>`;
    }else {
        tr += `<td class="uk-text-bold">모집완료</td>`;
    }

    tr += ` <td><span uk-icon="icon: triangle-down; ratio: 1.2"></span></td>
            <td><span uk-icon="icon: triangle-down; ratio: 1.2"></span></td>
            <td> - </td>
        </tr>
        </tbody>
    `;

    if(position.requestPosition.length !== 0){
        tr += `
            <tbody id="sub` + position.id + `" class="uk-text-0_8" hidden>
        `;
        for (let rePo of position.requestPosition) {
            if(rePo.status === 0){
                tr += ` 
                        <tr class="uk-animation-slide-top-small">
                        <td>` + position.position + `</td>
                        <td>` + position.content + `</td>
                        <td>지원요청</td>
                        <td>` + rePo.recruitMessage + `</td>
                        <td><button class="uk-button uk-button-small uk-text-nowrap" onclick="showMember(this)"` +
                            `data-member-loginId="`+ rePo.recruitMember.loginId +`"`+
                            `data-member-name="`+ rePo.recruitMember.name +`"`+
                            `data-member-email="`+ rePo.recruitMember.email +`"`+
                            `data-member-age="`+ rePo.recruitMember.age +`"`+
                            `data-member-position="`+ rePo.recruitMember.position +`"`+
                            `data-member-memberSkills='`+ JSON.stringify(rePo.recruitMember.memberSkills) +`'`+
                            `>보기</button></td>
                        <td>
                            <div class="uk-margin-small-bottom">
                                <button id="` + rePo.id + `" class="uk-button uk-button-primary uk-button-small uk-text-nowrap" onclick="acceptEvent(this)">수락</button>
                            </div>
                            <div>
                                <button id="` + rePo.id + `" class="uk-button uk-button-danger uk-button-small uk-text-nowrap" onclick="rejectEvent(this)">거절</button>
                            </div>
                        </td>
                        </tr>`;
            }else if(rePo.status === 1){
                tr += ` 
                        <tr class="uk-animation-slide-top-small">
                        <td>` + position.position + `</td>
                        <td>` + position.content + `</td>
                        <td>수락</td>
                        <td>` + rePo.recruitMessage + `</td>
                        <td><button class="uk-button uk-button-small uk-text-nowrap" onclick="showMember(this)"` +
                            `data-member-loginId="`+ rePo.recruitMember.loginId +`"`+
                            `data-member-name="`+ rePo.recruitMember.name +`"`+
                            `data-member-email="`+ rePo.recruitMember.email +`"`+
                            `data-member-age="`+ rePo.recruitMember.age +`"`+
                            `data-member-position="`+ rePo.recruitMember.position +`"`+
                            `data-member-memberSkills='`+ JSON.stringify(rePo.recruitMember.memberSkills) +`'`+
                            `>보기</button></td>
                        <td> - </td>
                        </tr>`;
            }else{
                tr += ` 
                        <tr class="uk-animation-slide-top-small">
                        <td>` + position.position + `</td>
                        <td>` + position.content + `</td>
                        <td>거절</td>
                        <td>` + rePo.recruitMessage + `</td>
                        <td><button class="uk-button uk-button-small uk-text-nowrap" onclick="showMember(this)"` +
                            `data-member-loginId="`+ rePo.recruitMember.loginId +`"`+
                            `data-member-name="`+ rePo.recruitMember.name +`"`+
                            `data-member-email="`+ rePo.recruitMember.email +`"`+
                            `data-member-age="`+ rePo.recruitMember.age +`"`+
                            `data-member-position="`+ rePo.recruitMember.position +`"`+
                            `data-member-memberSkills='`+ JSON.stringify(rePo.recruitMember.memberSkills) +`'`+
                        `>보기</button></td>
                        <td> - </td>
                        </tr>`;
            }
        }
        tr += `</tbody>`;
    }
    return tr;
}


function makePositionListDivInManage(position) {
    let div = "";

    div += `
    <div data-uk-grid="true">
        <div class="uk-width-auto">
            <span>포지션 명</span>
            <input class="uk-input uk-form-small" type="text" value="` + position.position + `"/>
        </div>
        <div class="uk-width-expand">
            <span>설명 기입</span>
            <input class="uk-input uk-form-width-xlarge uk-form-small" type="text" value="` + position.content + `"/>
        </div>
    </div>
    `;

    return div;
}


function makeTaskList(data) {
    let append = "";
    for (const task of data.tasks) {
        append += `
            <tr class="uk-background-muted">
                <td>`+task.startdate.substr(0, 10)+`</td>
                <td>`+task.enddate.substr(0, 10)+`</td>
                <td>`+task.position+`</td>
                <td>`+task.goal+`</td>`;

        if(task.status === 0){
            append +=` <td>진행중</td>
                        <td>
                            <button class="uk-button uk-button-primary uk-button-small uk-text-nowrap" onclick="updateStatus(`+task.id+`)">일정 완료</button>
                        </td>
                </tr>`;
        }else {
            append +=` <td>완료</td>
                       <td>-</td>
                </tr>`;
        }
    }
    return append;
}