<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title!"法律意见书"}</title>
    <style>
        body {
            font-family: SimSun, serif;
            font-size: 12pt;
            line-height: 1.8;
            margin: 2cm;
        }
        .header {
            text-align: center;
            font-size: 22pt;
            font-weight: bold;
            margin-bottom: 40px;
        }
        .sub-header {
            text-align: center;
            font-size: 14pt;
            margin-bottom: 30px;
        }
        .reference {
            margin-bottom: 30px;
        }
        .section {
            margin-bottom: 20px;
        }
        .section-title {
            font-weight: bold;
            font-size: 14pt;
            margin-bottom: 10px;
        }
        .footer {
            margin-top: 50px;
            text-align: right;
        }
    </style>
</head>
<body>
    <div class="header">法律意见书</div>
    
    <div class="sub-header">
        关于${subject!"_________________"}的法律意见书
    </div>
    
    <div class="reference">
        <p><b>致：</b>${recipient!"_________________"}</p>
        <p><b>编号：</b>${documentNo!"_________________"}</p>
        <p><b>日期：</b>${issueDate!"_________________"}</p>
    </div>
    
    <div class="section">
        <p class="section-title">一、引言</p>
        <p>${introduction!"本所接受贵方委托，就相关事项提供法律意见。本所律师依据中华人民共和国现行法律、法规、规章及其他规范性文件的规定，按照律师行业公认的业务标准、道德规范和勤勉尽责精神，对贵方提供的文件及有关事实进行了审查，并出具本法律意见书。"}</p>
    </div>
    
    <div class="section">
        <p class="section-title">二、声明事项</p>
        <p>${declarations!"1. 本所及经办律师依据《中华人民共和国律师法》《律师执业规则》等规定及本法律意见书出具日以前已经发生或者存在的事实，严格履行了法定职责，遵循了勤勉尽责和诚实信用原则，进行了充分的核查验证，保证本法律意见所认定的事实真实、准确、完整，所发表的结论性意见合法、准确，不存在虚假记载、误导性陈述或者重大遗漏。\n2. 本所仅就与本次事项有关的法律问题发表意见，不对有关会计、审计、资产评估等非法律专业事项发表意见。\n3. 本所同意将本法律意见书作为本次事项必备的法律文件，随同其他材料一同上报或公开披露。"}</p>
    </div>
    
    <div class="section">
        <p class="section-title">三、事实及背景</p>
        <p>${background!"_________________"}</p>
    </div>
    
    <div class="section">
        <p class="section-title">四、法律分析</p>
        <p>${legalAnalysis!"_________________"}</p>
    </div>
    
    <div class="section">
        <p class="section-title">五、结论性意见</p>
        <p>${conclusion!"_________________"}</p>
    </div>
    
    <div class="section">
        <p class="section-title">六、特别提示</p>
        <p>${specialNotes!"本法律意见书仅供委托人就本次事项之目的使用，未经本所书面同意，不得用作任何其他目的或由任何其他人使用。"}</p>
    </div>
    
    <div class="footer">
        <p>${lawFirm!"_________________"}</p>
        <p>负责人：${principal!"_________________"}</p>
        <p>经办律师：${lawyer!"_________________"}</p>
        <p>${date!"_________________"}</p>
    </div>
</body>
</html> 