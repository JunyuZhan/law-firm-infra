<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>出错了 - ${errorCode!"未知错误"}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #343a40;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .error-container {
            max-width: 600px;
            padding: 40px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 15px;
        }
        .error-message {
            font-size: 16px;
            margin-bottom: 30px;
            color: #6c757d;
        }
        .error-actions {
            margin-top: 20px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0069d9;
        }
        .btn-secondary {
            background-color: #6c757d;
            margin-left: 10px;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">${errorCode!"404"}</div>
        <div class="error-title">${errorTitle!"页面未找到"}</div>
        <div class="error-message">${errorMessage!"抱歉，您请求的页面不存在或已被移除。"}</div>
        
        <#if stackTrace?? && showStackTrace?? && showStackTrace>
        <div style="text-align: left; margin-top: 20px; margin-bottom: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 4px; overflow: auto; max-height: 200px;">
            <pre style="margin: 0; font-size: 12px; color: #6c757d;">${stackTrace}</pre>
        </div>
        </#if>
        
        <div class="error-actions">
            <a href="${homeUrl!'/'}" class="btn">返回首页</a>
            <#if previousUrl??>
            <a href="${previousUrl}" class="btn btn-secondary">返回上一页</a>
            </#if>
        </div>
    </div>
</body>
</html> 