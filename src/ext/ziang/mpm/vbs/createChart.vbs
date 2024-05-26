' 定义函数

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

' 生命变量
Dim objExcel, objWorkbook, objSheet, objShape, newExcelFile

Set args = WScript.Arguments
' 获取Java传递的参数总量
If args.Count < 0 Then
    WScript.Echo "没有提供Excel文件路径"
    WScript.Quit 1 ' 返回非零退出代码表示错误
End If

' 解析参数的内容
excelFile = args(0)
listString = URLDecode(args(1))
mapString = URLDecode(args(2))
' 解析List字符串
listArray = Split(listString, ",")

' 解析Map字符串
Set mapDict = CreateObject("Scripting.Dictionary")
For Each keyValue In Split(mapString, ",")
    arr = Split(keyValue, "=")
    mapDict(URLDecode(arr(0))) = URLDecode(arr(1))
Next

WScript.Echo "Excel file: " & excelFile
WScript.Echo "List items: " & Join(listArray, ", ")
For Each key In mapDict.Keys
    WScript.Echo key & ": " & mapDict(key)
Next
' 选择打开哪个应用
Set objExcel = CreateObject("Excel.Application")
' 设置是否展示打开
objExcel.Visible = True
Set objWorkbook = objExcel.Workbooks.Open(excelFile)
Set objSheet = objWorkbook.Sheets(1)

' 在工作表中绘制矩形
WScript.Echo "Drawing lines on Excel sheet..."
' 定义初始位置
startXIndex = 350
startYIndex = 140
' 定义横纵数量
index = 1
indexY = 1
defaultStartXIndex = 350
defaultStartYIndex = 140
' 定义一个Map 用于存储每个矩形的位置信息
' 应该还需要加个判断 用于确定这个是否是菱形
' 菱形的横纵坐标和位置起始点其实是不同
Dim map
Set map = CreateObject("Scripting.Dictionary")
defaultGap = 100
' 默认菱形的长宽
defaultDiamondWidth = 80
defaultDiamondHeight = 30
' 定义一个默认矩形的长宽
' 绘制图形
For Each element In listArray
    WScript.Echo element
    If element = "End" Or element = "Material" Then
        Set objShape1 = objSheet.Shapes.AddShape(1, startXIndex, startYIndex, 50, 20)
        map.Add element, Array(startXIndex, startYIndex)
        objShape1.TextFrame.Characters.Text = element
    ElseIf InStr(1, element, "R", 1) > 0 Then
        Set objShape1 = objSheet.Shapes.AddShape(4, startXIndex, startYIndex, defaultDiamondWidth, defaultDiamondHeight)
        map.Add element, Array(startXIndex, startYIndex)
        objShape1.TextFrame.Characters.Text = element
    Else
        Set objShape1 = objSheet.Shapes.AddShape(1, startXIndex, startYIndex, 50, 20)
        map.Add element, Array(startXIndex, startYIndex)
        objShape1.TextFrame.Characters.Text = element
    End If
    If index = 6 Then
        startYIndex = defaultStartYIndex
        startXIndex = startXIndex + defaultGap
        index = 1
    Else
        startYIndex = startYIndex + 40
        index = index + 1
    End If
Next


WScript.Echo "Drawing lines on Excel sheet..."
Dim valuesO
Dim valuesT
' 绘制连接线
For Each key In mapDict.Keys
' 起始位置获取坐标
    WScript.Echo key
    valuesO = map(key)
    ' 检查 valuesO 是否为数组
    If IsArray(valuesO) Then
        WScript.Echo "valuesO is an array"
        ' 获取结束位置坐标
        valuesT = map(mapDict(key))
        ' 检查 valuesT 是否为数组
        If IsArray(valuesT) Then
            WScript.Echo "valuesT is an array"
        Else
            WScript.Echo "valuesT is NOT an array"
        End If
        ' 输出调试信息
        WScript.Echo "key: " & key
        WScript.Echo "valuesO(0): " & valuesO(0) & ", valuesO(1): " & valuesO(1)
        WScript.Echo "mapDict(key): " & mapDict(key)
        WScript.Echo "valuesT(0): " & valuesT(0) & ", valuesT(1): " & valuesT(1)
        If valuesO(0) <> valuesT(0) And valuesO(1) <> valuesT(1) Then
            Set objShape = objSheet.Shapes.AddConnector(2, valuesO(0) + 50, valuesO(1) + 10, valuesT(0), valuesT(1) + 10) ' 1 表示直线连接器，(100, 100) 是起点坐标，(100, 200) 是终点坐标
            ' 设置箭头样式
            With objShape.Line
                .EndArrowheadStyle = 3 ' 结束箭头样式
                .EndArrowheadWidth = 2 ' 结束箭头宽度
                .EndArrowheadLength = 2 ' 结束箭头长度
            End With
        Else
            Set objShape = objSheet.Shapes.AddConnector(1, valuesO(0) + 25, valuesO(1) + 20, valuesT(0) + 25, valuesT(1)) ' 1 表示直线连接器，(100, 100) 是起点坐标，(100, 200) 是终点坐标
            ' 设置箭头样式
            With objShape.Line
                .EndArrowheadStyle = 3 ' 结束箭头样式
                .EndArrowheadWidth = 2 ' 结束箭头宽度
                .EndArrowheadLength = 2 ' 结束箭头长度
            End With
        End If
    Else
        WScript.Echo "valuesO is NOT an array"
    End If
Next

objWorkbook.Close
objExcel.Quit
Set objShape = Nothing
Set objLine = Nothing
Set objSheet = Nothing
Set objWorkbook = Nothing
Set objExcel = Nothing

WScript.Quit 0 ' 返回零退出代码表示成功执行
