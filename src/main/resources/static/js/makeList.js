function makeBoardContentDiv(content){
    let tbody = "";

    tbody += `
    <div class="board-card" onclick="openDetail(`+ content.id +`)">
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
            <tr>
                <td>` + position.position + `</td>
                <td>` + position.content + `</td>`;

    if(position.status === 0){
        tbody += `<td><button class="uk-button uk-button-small uk-text-nowrap">지원하기</button></td>`;
    }else {
        tbody += `<td><button class="uk-button uk-button-danger uk-button-small uk-text-nowrap">모집완료</button></td>`;
    }

    if(position.recruitMessage == null){
        tbody += `<td> - </td>`;
    }else {
        tbody += `<td>` + position.recruitMessage + `</td>`;
    }

    tbody += `
            </tr>
            `;

    return tbody;
}