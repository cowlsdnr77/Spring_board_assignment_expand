// $(document).ready(function () {
//     let selected_id = parseInt(location.href.substr(location.href.lastIndexOf('=') + 1)) //url의 id=?에서 ? 값을 id로 가져옴
//     showOneBoard(selected_id);
// })

// 목록에서 하나 클릭했을때 상세 페이지로 이동
function showOneBoard(id) {
    $.ajax({
        type: "GET",
        url: `/api/boards/${id}`,
        success: function (response){
            // 목록 비우기
            $('#one-board').empty();

            let timeArray = response.modifiedAt.split('T')
            let time = timeArray[0] + " " + timeArray[1].substr(0,5)

            // HTML 만들어서 붙이기!
            let tempHtml = `
                            <a href="/"><h2>Board</h2></a>
                            
                            <div id="show_one_board">
                                <dt class="col-sm-3">제목</dt>
                                <dd class="col-sm-9" id="title">${response.title}</dd>
                          
                                <dt class="col-sm-3">작성자</dt>
                                <dd class="col-sm-9" id="username">${response.username}</dd>      
                        
                                <dt class="col-sm-3">작성일자</dt>
                                <dd class="col-sm-9">${time}</dd>
                        
                                <dt class="col-sm-3">내용</dt>
                                <dd class="col-sm-9">
                                    <p id="content">
                                        ${response.content}
                                    </p>
                                </dd>
                            </div>
                           
                            <div class="form-group" style="display: none" >
                                <label for="SelectedTitle">제목</label>
                                <input class="form-control col-sm-5" id="SelectedTitle">
                            </div>
                            <div class="form-group" style="display: none">
                                <label for="SelectedContent">내용</label>
                                <textarea class="form-control col-sm-7" id="SelectedContent" rows="8"></textarea>
                            </div>
                            
                            
                            <button type="button" class="btn btn-outline-primary mr-2" id="changeToUpdate" onClick="changeToUpdateBoard(${response.id})">수정하기</button>
                            <button type="button" class="btn btn-outline-primary mr-2" id="realUpdate" style="display: none" onClick="UpdateOneBoard(${response.id})">수정하기</button>
                            <button type="button" class="btn btn-outline-primary" onclick="deleteOneBoard(${response.id})">삭제하기</button>`
            $('#one-board').append(tempHtml);
        }
    })
}

//처음 수정하기 버튼 눌렀을때
function changeToUpdateBoard(id) {
    let orgin_title = $('#title').text()
    let orgin_content = $('#content').text().trim()

    $('#show_one_board').hide()
    $('#changeToUpdate').hide()

    $('#SelectedTitle').val(orgin_title)
    $('#SelectedContent').val(orgin_content)

    $('.form-group').show()
    $('#realUpdate').show()
}

//두번째 수정하기 버튼을 눌렀을때
function UpdateOneBoard(id) {
    let title = $('#SelectedTitle').val()
    let content = $('#SelectedContent').val()

    let boardDto = {
        "title": title,
        "content": content
    }
    $.ajax({
        type: "PUT",
        url: `/api/boards/${id}`,
        data: JSON.stringify(boardDto),
        contentType: "application/json",
        success: function (response){
            alert("게시물이 수정되었습니다.")
            window.location.href = "/"
        }
    })
}
//삭제하기 버튼 눌렀을때
function deleteOneBoard(id) {

    $.ajax({
        type: "DELETE",
        url: `/api/boards/${id}`,
        success: function (response){
            alert("게시물이 삭제되었습니다.")
            window.location.href = "/"
        }
    })
}