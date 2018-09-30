$(function () {
    //markdown工具栏
    $(".md-toolbar").empty();
    $(".md-toolbar").append("<ul>\n" +
        "<li><span title=\"Blod\"><b>B</b></span></li>\n" +
        "<li><span style=\"font-weight: bold; font-style: italic\" title=\"Italic\">&Iota;</span></li>\n" +
        "<li><span style=\"text-decoration: line-through\" title=\"Strikethrough\">U</span></li>\n" +
        "<li><span style=\"text-decoration: underline\" title=\"Insert\">U</span></li>\n" +
        "<li><span title=\"Mark\"><b>&Mu;</b></span></li>\n" +
        "<li style=\"color: lightgray\">|</li>\n" +
        "<li><span title=\"Heading 1\">h1</span></li>\n" +
        "<li><span title=\"Heading 2\">h2</span></li>\n" +
        "<li><span title=\"Heading 3\">h3</span></li>\n" +
        "<li><span title=\"Heading 4\">h4</span></li>\n" +
        "<li><span title=\"Heading 5\">h5</span></li>\n" +
        "<li><span title=\"Heading 6\">h6</span></li>\n" +
        "<li style=\"color: lightgray\">|</li>\n" +
        "<li><span style=\"font-weight: bold\" title=\"Horizontal rule\">一</span></li>\n" +
        "<li><span style=\"font-weight: bold\" title=\"Quote\">&lt;</span></li>\n" +
        "<li><span title=\"Link\">₪</span></li>\n" +
        "<li><span title=\"Image\">▣</span></li>\n" +
        "<li><span title=\"Code\">&lt;\/&gt;</span></li>\n" +
        "<li><span style=\"margin-left: 8px\" title=\"Code Block\">≡</span></li>\n" +
        "<li><span title=\"Table\">▦</span></li>\n" +
        "</ul>");
    var specialTitle;
    $(".md-toolbar ul li span").mousedown(function (e) {
        e.preventDefault();
        var mdText = document.getElementById('markdownText');
        var title = $(this).attr("title");
        var selected = window.getSelection().toString();
        var start = mdText.selectionStart;
        var end = mdText.selectionEnd;
        var result = changeText(title, selected);
        mdText.value = mdText.value.substring(0,start) + result + mdText.value.substring(end);
        //格式化后光标位置
        if (title == 'Link') {
            mdText.selectionEnd = mdText.value.length - (result.length - selected.length)/2 - 1;
        } else if (title != specialTitle) {
            mdText.selectionEnd = mdText.value.length - (result.length - selected.length)/2;
        }
        $('#markdownText').keyup();
        $('#markdownText').focus();

    });
    function changeText(title, selected) {
        switch (title) {
            case "Blod":selected="**"+selected+"**";break;
            case "Italic":selected="*"+selected+"*";break;
            case "Strikethrough":selected="~~"+selected+"~~";break;
            case "Insert":selected="++"+selected+"++";break;
            case "Mark":selected="=="+selected+"==";break;
            case "Heading 1":selected="# "+selected;specialTitle = title;break;
            case "Heading 2":selected="## "+selected;specialTitle = title;break;
            case "Heading 3":selected="### "+selected;specialTitle = title;break;
            case "Heading 4":selected="#### "+selected;specialTitle = title;break;
            case "Heading 5":selected="##### "+selected;specialTitle = title;break;
            case "Heading 6":selected="###### "+selected;specialTitle = title;break;
            case "Horizontal rule":selected= selected + "\n--- ";specialTitle = title;break;
            case "Quote":selected="\n> "+selected;specialTitle = title;break;
            case "Link":selected="["+selected+"]()";break;
            case "Image":selected="!["+selected+"]()";break;
            case "Code":selected="`"+selected+"`";break;
            case "Code Block":selected="```\n"+selected+"\n```";break;
            case "Table":selected="\n\nheader 1 | header 2\n" + "---|---\n" + "row 1 col 1 | row 1 col 2\n" + "row 2 col 1 | row 2 col 2\n"+selected;
                 specialTitle = title;break;
            default:break;
        }
        return selected;
    }
});