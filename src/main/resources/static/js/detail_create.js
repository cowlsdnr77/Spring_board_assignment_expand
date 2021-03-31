//게시물 생성하기
function createOneBoard() {
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
}