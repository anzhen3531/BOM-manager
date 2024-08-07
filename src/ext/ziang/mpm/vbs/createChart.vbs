' 定义转义函数
Function URLDecode(encodedStr)
    Dim decodedStr, i, c
    decodedStr = ""
    i = 1
    Do While i <= Len(encodedStr)
        c = Mid(encodedStr, i, 1)
        If c = "%" Then
            decodedStr = decodedStr & Chr(CLng("&H" & Mid(encodedStr, i + 1, 2)))
            i = i + 3
        Else
            decodedStr = decodedStr & c
            i = i + 1
        End If
    Loop
    URLDecode = decodedStr
End Function

' 设置默认样式函数

Function SetDefaultStyle(shape)
    shape.Fill.ForeColor.RGB = RGB(255, 255, 255)
    shape.Line.ForeColor.RGB = RGB(0, 0, 0)
    shape.TextFrame.Characters.Font.Color = RGB(0, 0, 0)
    shape.TextFrame2.WordWrap = True
    shape.TextFrame.AutoSize = False
    shape.TextFrame.VerticalOverflow = 0
    shape.TextFrame.MarginBottom = 0
    shape.TextFrame.MarginLeft = 0
    shape.TextFrame.MarginRight = 0
    shape.TextFrame.MarginTop = 0
    shape.TextFrame.HorizontalAlignment = - 4108 ' xlCenter
    shape.TextFrame.VerticalAlignment = - 4108 ' xlCenter
End Function

' 声明变量
Dim objExcel, objWorkbook, objSheet, objShape
Set args = WScript.Arguments
If args.Count < 0 Then
    WScript.Echo "没有提供Excel文件路径"
    WScript.Quit 1
End If

' 取出java传递的值 进行赋值
excelFile = args(0)
listString = URLDecode(args(1))
mapString = URLDecode(args(2))
listArray = Split(listString, ",")

' 将传递的mapString 转换为Map集合
Set mapDict = CreateObject("Scripting.Dictionary")
For Each keyValue In Split(mapString, ",")
    arr = Split(keyValue, "=")
    mapDict(URLDecode(arr(0))) = URLDecode(arr(1))
Next

' 打开一个ExcelApp
Set objExcel = CreateObject("Excel.Application")
' 显示打开
objExcel.Visible = True
Set objWorkbook = objExcel.Workbooks.Open(excelFile)
Set objSheet = objWorkbook.Sheets(1)
newFilePath = Replace(excelFile, "WorkflowPicTepm", "WorkflowPic")
startXIndex = 350
startYIndex = 140
index = 1
indexY = 1
defaultStartXIndex = 350
defaultStartYIndex = 140
endYIndex = 0
endXIndex = 0
For Each node In objWorkbook.Names
    If InStr(1, node.Name , "picStartIndex", 1) > 0 Then
        defaultStartXIndex = node.RefersToRange.Left
        startXIndex = node.RefersToRange.Left
        startYIndex = node.RefersToRange.Top
        defaultStartYIndex = node.RefersToRange.Top
    ElseIf InStr(1, node.Name , "picEndX", 1) > 0 Then
        endXIndex = node.RefersToRange.Left - 20
    ElseIf InStr(1, node.Name , "picEndY", 1) > 0 Then
        endYIndex = node.RefersToRange.Top - 20
    End if
    cellTop = node.RefersToRange.Top
    cellLeft = node.RefersToRange.Left
Next

Set coordinates = CreateObject("Scripting.Dictionary")
coordinates.Add "Row", row
coordinates.Add "Column", column
WScript.Echo "Drawing lines on Excel sheet..."
Dim map
Set map = CreateObject("Scripting.Dictionary")
defaultGap = 100
defaultDiamondWidth = 90
defaultDiamondHeight = 60
flag = False

WScript.Echo "Drawing Model on Excel sheet..."
For Each element In listArray
' WScript.Echo element
    If element = "End" Or element = "Material" Then
        Set objShape1 = objSheet.Shapes.AddShape(69, startXIndex, startYIndex, 65, 20)
        objShape1.TextFrame.Characters.Text = element
        SetDefaultStyle(objShape1)
        newWidth = objShape1.Width
        newHeight = objShape1.Height
        map.Add element, Array(startXIndex, startYIndex, 65, 20)
    ElseIf InStr(1, element, """D""", 1) > 0 Then
        Set objShape1 = objSheet.Shapes.AddShape(1, startXIndex, startYIndex, 65, 20)
        objShape1.TextFrame.Characters.Text = Replace(element, "+", " ")
        SetDefaultStyle(objShape1)
        newWidth = objShape1.Width
        newHeight = objShape1.Height
        map.Add element, Array(startXIndex, startYIndex, 65, 20)
    ElseIf InStr(1, element, "$F", 1) > 0 Then
        If (startYIndex + defaultDiamondHeight) > endYIndex Then
            startYIndex = defaultStartYIndex
            startXIndex = startXIndex + defaultGap
            index = 1
            flag = True
        Else
            flag = True
        End If
        Set objShape1 = objSheet.Shapes.AddShape(4, startXIndex - 13, startYIndex, defaultDiamondWidth, defaultDiamondHeight)
        objShape1.TextFrame.Characters.Text = Replace(element, "$F", "")
        SetDefaultStyle(objShape1)
        newWidth = objShape1.Width
        newHeight = objShape1.Height
        ' WScript.Echo "newHeight = " & newHeight & " newWidth = " & newWidth
        map.Add element, Array(startXIndex - 13, startYIndex, newWidth, newHeight)
    Else
        If  Len(element) > 8 Then
            rate = Len(element) / 8
            rateHeight = rate * 10
            Set objShape1 = objSheet.Shapes.AddShape(1, startXIndex, startYIndex, 65, 20 + rateHeight)
            objShape1.TextFrame.Characters.Text = element
            SetDefaultStyle(objShape1)
            map.Add element, Array(startXIndex, startYIndex, 65, 20 + rateHeight)
            startYIndex = startYIndex + rateHeight
        Else
            Set objShape1 = objSheet.Shapes.AddShape(1, startXIndex, startYIndex, 65, 20)
            objShape1.TextFrame.Characters.Text = element
            SetDefaultStyle(objShape1)
            map.Add element, Array(startXIndex, startYIndex, 65, 20)
        End If
    End If
    If (startYIndex + 50) > endYIndex Then
        startYIndex = defaultStartYIndex
        startXIndex = startXIndex + defaultGap
        index = 1
    Else
        If flag Then
            startYIndex = startYIndex + defaultDiamondHeight + 20
            If (startYIndex + 50) > endYIndex Then
                startYIndex = defaultStartYIndex
                startXIndex = startXIndex + defaultGap
                index = 1
            End If
            index = index + 1
            flag = False
        Else
            startYIndex = startYIndex + 40
            index = index + 1
        End If
    End If
Next


WScript.Echo "Drawing lines on Excel sheet..."
Dim valuesO
Dim valuesT
For Each key In mapDict.Keys
' WScript.Echo key
    valuesO = map(key)
    If IsArray(valuesO) Then
    ' WScript.Echo "valuesO is an array"
        valuesT = map(mapDict(key))
        '        WScript.Echo "key: " & key
        '        WScript.Echo "valuesO(0): " & valuesO(0) & ", valuesO(1): " & valuesO(1) & ", valuesO(2): " & valuesO(2) & ", valuesO(3): " & valuesO(3)
        '        WScript.Echo "mapDict(key): " & mapDict(key)
        '        WScript.Echo "valuesT(0): " & valuesT(0) & ", valuesT(1): " & valuesT(1) & ", valuesT(2): " & valuesT(2) & ", valuesO(3): " & valuesT(3)
        If valuesO(0) <> valuesT(0) And valuesO(1) <> valuesT(1) And valuesO(1) > valuesT(1) Then
            Set objShape = objSheet.Shapes.AddConnector(2, valuesO(0) + (valuesO(2)), valuesO(1) + (valuesO(3) / 2), valuesT(0) , valuesT(1) + (valuesT(3) / 2))
            ' 设置箭头样式
            With objShape.Line
                .EndArrowheadStyle = 3
                .EndArrowheadWidth = 2
                .EndArrowheadLength = 2
                .ForeColor.RGB = RGB(0, 0, 0)
            End With
        Else
            Set objShape = objSheet.Shapes.AddConnector(1, valuesO(0) + (valuesO(2) / 2), valuesO(1) + valuesO(3), valuesT(0) + (valuesT(2) / 2), valuesT(1)) ' 1 表示直线连接器，(100, 100) 是起点坐标，(100, 200) 是终点坐标
            ' 设置箭头样式
            With objShape.Line
                .EndArrowheadStyle = 3
                .EndArrowheadWidth = 2
                .EndArrowheadLength = 2
                .ForeColor.RGB = RGB(0, 0, 0)
            End With
        End If
    Else
        WScript.Echo "valuesO is NOT an array"
    End If
Next

On Error Resume Next
objWorkbook.Save True
' 关闭工作簿，不保存更改
objWorkbook.Close
' 退出 Excel
objExcel.Quit
' 释放对象
Set objShape = Nothing
Set objLine = Nothing
Set objSheet = Nothing
Set objWorkbook = Nothing
Set objExcel = Nothing
