@echo off
echo 正在删除有问题的文件...

del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\controller\RoleController.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\security\filter\JsonLoginFilter.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\controller\UserController.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\service\support\PermissionCheckerImpl.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\exception\AuthException.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\security\provider\CustomAuthenticationProvider.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\security\handler\LoginSuccessHandler.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\exception\GlobalExceptionHandler.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\service\impl\PermissionServiceImpl.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\service\impl\UserPersonnelServiceImpl.java"
del /q "law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth\config\CorsConfig.java"

echo 文件删除完成！