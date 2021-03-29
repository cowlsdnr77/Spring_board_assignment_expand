$(document).ready(function () {
    showBoard();
})

//index.html 에 board 목록 보여줌
function showBoard() {
    $.ajax({
        type: "GET",
        url: "/api/boards",
        success: function (response){
            // 목록 비우기
            $('#board-container').empty();
            // for 문마다 관심 상품 HTML 만들어서 관심상품 목록에 붙이기!
            for(let i=0 ; i<response.length ; i++) {
                let boardItem = response[i];
                let tempHtml = addHTML(boardItem);
                $('#board-container').append(tempHtml);
            }
        }
    })
}

//목록 하나씩 html에 append함
function addHTML(boardItem) {
    if (boardItem) {
        let timeArray = boardItem.modifiedAt.split('T')
        let time = timeArray[0] + " " + timeArray[1].substr(0,5)
        return `<tr onClick="location.href='detail_show.html?id=${boardItem.id}'" style="cursor:pointer">    
                    <td>${boardItem.title}</td>
                    <td>${boardItem.username}</td>
                    <td>${time}</td>
            </tr>`
    }
    return "nothing"
}


