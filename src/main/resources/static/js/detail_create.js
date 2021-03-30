//게시물 생성하기
function createOneBoard() {
    let title = $('#SelectedTitle').val()
    let content = $('#SelectedContent').val()

    let boardDto = {
        "title": title,
        "content": content
    }
    $.ajax({
        type: "POST",
        url: "/api/boards",
        data: JSON.stringify(boardDto),
        contentType: "application/json",
        success: function(response) {
            alert("게시글이 작성되었습니다.")
            window.location.href = "/"
        }
    })
}