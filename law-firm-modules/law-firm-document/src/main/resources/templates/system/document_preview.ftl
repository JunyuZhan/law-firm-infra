<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文档预览 - ${documentName!"未命名文档"}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .preview-container {
            position: relative;
            max-width: 1000px;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .preview-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
            margin-bottom: 20px;
        }
        .preview-title {
            font-size: 20px;
            font-weight: bold;
        }
        .preview-actions {
            display: flex;
            gap: 10px;
        }
        .preview-actions button {
            padding: 5px 10px;
            border: none;
            background-color: #2c7be5;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        .preview-actions button:hover {
            background-color: #1a68d1;
        }
        .preview-content {
            position: relative;
            min-height: 500px;
            padding: 20px;
            border: 1px solid #eee;
        }
        .watermark {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            pointer-events: none;
            z-index: 100;
        }
        .watermark-text {
            transform: rotate(-45deg);
            color: rgba(0, 0, 0, 0.1);
            font-size: 48px;
            font-weight: bold;
            white-space: nowrap;
            user-select: none;
        }
        .preview-footer {
            margin-top: 20px;
            text-align: center;
            color: #666;
            font-size: 12px;
        }
        .preview-info {
            margin-bottom: 20px;
            font-size: 14px;
            color: #666;
        }
        .info-item {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
    <div class="preview-container">
        <div class="preview-header">
            <div class="preview-title">${documentName!"未命名文档"}</div>
            <div class="preview-actions">
                <button onclick="window.print()">打印</button>
                <button onclick="window.location.href='${downloadUrl!""}'">下载</button>
            </div>
        </div>
        
        <div class="preview-info">
            <div class="info-item"><strong>创建时间：</strong>${createTime!"--"}</div>
            <div class="info-item"><strong>创建人：</strong>${creator!"--"}</div>
            <div class="info-item"><strong>文件大小：</strong>${fileSize!"--"}</div>
            <div class="info-item"><strong>文件类型：</strong>${fileType!"--"}</div>
        </div>
        
        <div class="preview-content">
            <#if watermarkEnabled?? && watermarkEnabled>
            <div class="watermark">
                <div class="watermark-text">${watermarkText!"预览版本 仅供查看"}</div>
            </div>
            </#if>
            
            <#if documentContent??>
                ${documentContent}
            <#else>
                <div style="text-align: center; padding: 100px 0;">
                    <p>文档内容加载中或无法预览...</p>
                </div>
            </#if>
        </div>
        
        <div class="preview-footer">
            <p>本文档由法律事务管理系统提供 &copy; ${.now?string('yyyy')} ${companyName!"律师事务所"}</p>
            <p>预览时间: ${.now?string('yyyy-MM-dd HH:mm:ss')}</p>
        </div>
    </div>
</body>
</html> 