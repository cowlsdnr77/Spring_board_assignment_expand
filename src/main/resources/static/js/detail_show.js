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
    let selected_id = parseInt(id)
    let title = $('#SelectedTitle').val()
    let content = $('#SelectedContent').val()

    if(title.replace(/\s| /gi,"").length === 0) {
        alert("게시글 제목을 입력해주세요.")
    } else if (content.replace(/\s| /gi,"").length === 0){
        alert("게시글 내용을 입력해주세요.")
    } else {
        let boardDto = {
            "title": title,
            "content": content
        }
        $.ajax({
            type: "PUT",
            url: `/api/boards/${selected_id}`,
            data: JSON.stringify(boardDto),
            contentType: "application/json",
            success: function (response){
                alert("게시물이 수정되었습니다.")
                window.location.href = "/"
            }
        })
    }
}
//삭제하기 버튼 눌렀을때
function deleteOneBoard(id) {
    let selected_id = parseInt(id)
    $.ajax({
        type: "DELETE",
        url: `/api/boards/${selected_id}`,
        success: function (response){
            alert("게시물이 삭제되었습니다.")
            window.location.href = "/"
        }
    })
}

function redirectToLogin() {
    alert("로그인이 필요합니다.")
    window.location.href = "/user/login"
}

//댓글 쓰기 버튼 눌렀을때
function createOneComment(boardId) {
    let selected_id = parseInt(boardId)
    let content = $('#comment-write').val()
    if (content.replace(/\s| /gi,"").length === 0) {
        alert("댓글 내용을 입력해주세요.")
    } else {
        let commentDto = {
            "content": content
        }
        $.ajax({
            type: "POST",
            url: `/api/comments?boardId=${selected_id}`,
            data: JSON.stringify(commentDto),
            contentType: "application/json",
            success: function(response) {
                alert("댓글이 작성되었습니다.")
                window.location.href = `/boards?id=${boardId}`
            }
        })
    }
}

//댓글 수정 버튼 눌렀을때
function clickUpdateCommentBtn(commentId) {
    $(`.comment_${commentId}`).hide()
    $(`.comment_save_${commentId}`).show()
    $(`#editing_${commentId}`).text("수정중...")
}

//댓글 저장 버튼 눌렀을때
function updateOneComment(commentId,boardId) {
    let content = $(`#comment_update_${commentId}`).val()
    if( content.replace(/\s| /gi,"").length === 0) { //미입력 또는 공뱁 입력 방지
        alert("댓글 내용을 입력해주세요.")
    } else{
        let commentDto = {
            "content": content
        }
        $.ajax({
            type: "PUT",
            url: `/api/comments/${commentId}`,
            data: JSON.stringify(commentDto),
            contentType: "application/json",
            success: function (response){
                alert("댓글이 수정되었습니다.")
                window.location.href = `/boards?id=${boardId}`
            }
        })
    }
}

//댓글 삭제 버튼 눌렀을때
function deleteOneComment(commentId,boardId) {
    let selected_id = parseInt(commentId)
    if (confirm("정말로 삭제하시겠습니까?") === true) {
        $.ajax({
            type: "DELETE",
            url: `/api/comments/${selected_id}`,
            success: function (response){
                alert("댓글이 삭제되었습니다.")
                window.location.href = `/boards?id=${boardId}`
            }
        })
    }
}