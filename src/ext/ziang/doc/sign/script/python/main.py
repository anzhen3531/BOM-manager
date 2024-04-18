import io
import os
import sys
import traceback

from win32com import client


def signDoc(filePath, signMapping, newFilePath):
    try:
        app = client.Dispatch('Word.Application')
        app.Visible = True
        word = app.Documents.Open(filePath)
        bookmarks = word.BookMarks
        # 遍历书签
        for key in signMapping:
            value = signMapping[key]
            fruits = value.split(' ')
            userPicPath = fruits[0]
            for bookmark in bookmarks:
                timeSignName = key + '日期'
                start = bookmark.Range.Start
                end = bookmark.Range.End
                if key in bookmark.Name and "日期" not in bookmark.Name:
                    signFilePath = os.path.join(r"D:\pythonProject\handlerWordToDocx\temp", userPicPath + ".png")
                    if os.path.exists(signFilePath):
                        range_to_delete = word.Range(start + 2, end - 1)
                        range_to_delete.Text = ''
                        bookmark.Range.Select()
                        inline_shape = bookmark.Range.InlineShapes.AddPicture(signFilePath)
                        inline_shape.Width = 50
                        inline_shape.Height = 15
                    else:
                        range_to_delete = word.Range(start + 2, end - 1)
                        range_to_delete.Text = ''
                        bookmark.Range.Select()
                        bookmark.Range.text = userPicPath
                elif timeSignName in bookmark.Name:
                    start = bookmark.Range.Start
                    end = bookmark.Range.End
                    range_to_delete = word.Range(start + 2, end - 1)
                    range_to_delete.Text = ''
                    bookmark.Range.Select()
                    bookmark.Range.text = fruits[1]
        # 另存为一个新文件
        word.SaveAs(newFilePath)
        word.Close(-1)
        app.Quit()
        return newFilePath
    except Exception as e:
        traceback.print_exc()
        print(e)


# 处理字典
def handlerDict(str):
    string_representation = str.strip("{}")
    key_value_pairs = string_representation.split(", ")
    signMapping = {}
    for pair in key_value_pairs:
        key, value = pair.split("=")
        signMapping[key.strip()] = value.strip()
    return signMapping


if __name__ == "__main__":
    # 判断文件类型 如果是excel 走调用excel的
    # 如果是word走调用word的
    # 可视化转换也采用这种方式
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
    signMapping = handlerDict(sys.argv[3])
    result = signDoc(sys.argv[1], signMapping, sys.argv[2])
    print(result)
