function makeBoardContentDiv(content){
    let tbody = "";

    tbody += `
    <div class="board-card uk-margin-bottom" onclick="openDetail(`+ content.id +`)">
        <div class="uk-card uk-card-default">
            <div class="uk-card-header">
                <div class="uk-text-right uk-margin-bottom">`;

    if(content.status === 0){
        tbody += `<button class="uk-button uk-button-small uk-text-nowrap">모집중</button>`;
    }else {
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

function makeRecruitPositionListDiv(position) {
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
