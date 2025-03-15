<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title!"合同模板"}</title>
    <style>
        body {
            font-family: SimSun, serif;
            font-size: 12pt;
            line-height: 1.5;
            margin: 2cm;
        }
        .header {
            text-align: center;
            font-size: 18pt;
            font-weight: bold;
            margin-bottom: 30px;
        }
        .party-info {
            margin-bottom: 20px;
        }
        .clause {
            margin-bottom: 15px;
        }
        .clause-title {
            font-weight: bold;
        }
        .signature {
            margin-top: 50px;
            display: flex;
            justify-content: space-between;
        }
        .sign-block {
            width: 45%;
        }
    </style>
</head>
<body>
    <div class="header">${contractName!"服务合同"}</div>
    
    <div class="party-info">
        <p><b>甲方（委托方）：</b>${partyA!"_________________"}</p>
        <p><b>地址：</b>${partyAAddress!"_________________"}</p>
        <p><b>联系人：</b>${partyAContact!"_________________"}</p>
        <p><b>联系电话：</b>${partyAPhone!"_________________"}</p>
    </div>
    
    <div class="party-info">
        <p><b>乙方（受托方）：</b>${partyB!"_________________"}</p>
        <p><b>地址：</b>${partyBAddress!"_________________"}</p>
        <p><b>联系人：</b>${partyBContact!"_________________"}</p>
        <p><b>联系电话：</b>${partyBPhone!"_________________"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第一条 服务内容</p>
        <p>${serviceContent!"甲方委托乙方提供法律服务，乙方接受甲方委托。"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第二条 服务期限</p>
        <p>服务期限自${startDate!"_________________"}起至${endDate!"_________________"}止。</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第三条 服务费用</p>
        <p>本合同服务费用为人民币${serviceFee!"_________________"}元整。</p>
        <p>${paymentTerms!"甲方应在本合同签订后7日内支付服务费用的50%，剩余50%在服务完成后支付。"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第四条 双方权利义务</p>
        <p>${obligations!"1. 甲方应提供乙方履行本合同所需的资料、信息，并对其真实性、合法性负责。\n2. 乙方应按照相关法律法规和行业规范提供专业服务。"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第五条 保密条款</p>
        <p>${confidentiality!"双方对在合作过程中知悉的对方商业秘密和其他保密信息负有保密义务，未经信息所有方书面同意，不得向第三方披露。"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第六条 违约责任</p>
        <p>${breach!"任何一方违反本合同约定，应当承担违约责任，赔偿因此给对方造成的损失。"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第七条 争议解决</p>
        <p>${dispute!"双方因履行本合同发生争议，应当协商解决；协商不成的，可向合同签订地有管辖权的人民法院提起诉讼。"}</p>
    </div>
    
    <div class="clause">
        <p class="clause-title">第八条 其他</p>
        <p>${others!"本合同一式两份，甲乙双方各执一份，具有同等法律效力。\n本合同自双方签字盖章之日起生效。"}</p>
    </div>
    
    <div class="signature">
        <div class="sign-block">
            <p><b>甲方（盖章）：</b>__________________</p>
            <p><b>代表人签字：</b>__________________</p>
            <p><b>日期：</b>__________________</p>
        </div>
        <div class="sign-block">
            <p><b>乙方（盖章）：</b>__________________</p>
            <p><b>代表人签字：</b>__________________</p>
            <p><b>日期：</b>__________________</p>
        </div>
    </div>
</body>
</html> 