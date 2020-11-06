/*
package com.example.a11699.androidxallstudy.face2oop

import android.view.View
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

*/
/**
 * @Author Yu
 * @Date 2020/11/5 11:10
 * @Description 定义PointCut切入点
 *//*


@Aspect
class FastClickAspect {
    //定义一个切入点 View.OnClickListener#onClick()方法
    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    fun methodViewOnClick(){

    }
    //定义环绕增强 包括methodViewOnclick切入点
    @Around("methodViewOnClick()")
    fun aroundViewOnCLick(jointPoint:ProceedingJoinPoint){
        //取出目标对象
        var target = jointPoint.args[0] as View
        //根据点击间隔是否超过2000，判断是否为快速点击
        if(!FaseClickCheckUtil.isFastClick(target,2000)){
            jointPoint.proceed()
        }
    }
}
*/
