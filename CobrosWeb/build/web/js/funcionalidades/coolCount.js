/**
 * Copyright (C) 2002-2003, CodeHouse.com. All rights reserved.
 * CodeHouse(TM) is a registered trademark.
 *
 * THIS SOURCE CODE MAY BE USED FREELY PROVIDED THAT
 * IT IS NOT MODIFIED OR DISTRIBUTED, AND IT IS USED
 * ON A PUBLICLY ACCESSIBLE INTERNET WEB SITE.
 *
 * Script Name: Cool Countdown/Cool Count-up
 *
 * You can obtain this script at http://www.codehouse.com
 */

$UMSG_INVALID_HOURS='You have entered invalid input into the hours field.\n'+'Enter an amount of hours in the range of 0 - 99';
$UMSG_INVALID_MINUTES='You have entered invalid input into the minutes field.\n'+'Enter an amount of minutes in the range of 0 - 59';
$UMSG_INVALID_SECONDS='You have entered invalid input into the seconds field.\n'+'Enter an amount of minutes in the range of 0 - 59';
$UMSG_NO_INPUT='You have not entered a desired countdown time';
$UMSG_CLOSE_ACTIVE='You are attempting to exit this page while a timer is still active.\n'+'Doing so will cause the timer to terminate. When you press the "OK"\n'+'button, you will be given an opportunity to abort exiting this page';
if(navigator.userAgent.match(/(?!Opera)(MSIE)|(Gecko)/))
    new function(){
    var DOM_TARGET="target";
    var TARGET=window.attachEvent?"srcElement":DOM_TARGET;
    var MAX_TICKS=60*60*99+60*59+59;var ORIG_TITLE=document.title;var DLG_URL="stop_alarm.htm";
    var IE_DLG_STYLE="center:yes; dialogWidth:325px; dialogHeight:175px; status:no";
    var GK_DLG_STYLE="dependent,width=325,height=175";var COUNT_DOWN="CH_dtimer";
    var COUNT_UP="CH_utimer";var clockList=[];if(window.attachEvent)attachEvent("onbeforeunload",function(){
        for(var i in clockList){
            if(clockList[i].isActive()){
                alert($UMSG_CLOSE_ACTIVE);window.event.returnValue=false;break;
            }
        }
    });
    function addListener(e,ev,listener){
        if(window.attachEvent){
            e.attachEvent("on"+ev,listener);
        }else{
            e.addEventListener(ev,listener,false);
        }
    }function resetHandler(clock){
        clock.reset();clock.eReset.disabled=true;clock.ePause.disabled=true;
        clock.eResume.disabled=true;
        clock.eStart.disabled=false;
        if(clock.type==COUNT_DOWN){
            clock.disableTf(false);clock.eMinutes.focus();
        }
    }function initHandlers(clock){
        function handler(event){

            switch(event[TARGET]){
                case clock.ePause:
                    clock.stop();
                    clock.eResume.disabled=false;
                    clock.eResume.focus();
                    clock.ePause.disabled=true;
                    break;
                case clock.eResume:
                    clock.start();
                    clock.eResume.disabled=true;
                    clock.eReset.focus();
                    clock.ePause.disabled=false;
                    break;
                case clock.eReset:
                    resetHandler(clock);
                    break;
                case clock.eStart:  
                    if(clock.eStart.disabled)
                        return;                   
                default:                      
                    function getNumberInput(e){
                        var s=e.value;
                        if(!s.length){
                            return 0;
                        }var i=parseInt(e.value);if(isNaN(i)){
                            switch(e){
                                case clock.eHours:alert($UMSG_INVALID_HOURS);break;case clock.eMinutes:alert($UMSG_INVALID_MINUTES);break;case clock.eSeconds:alert($UMSG_INVALID_SECONDS);
                            }
                            e.value="";
                            e.focus();
                        }else if(s.length!=new String(i).length){
                            e.value=new String(i);
                        }return i;
                    }
                  //  console.log(clock.type);
                    if(clock.type==COUNT_UP){
                        clock.eStart.disabled=true;
                        clock.ePause.disabled=false;
                        clock.eReset.disabled=false;
                        clock.eReset.focus();
                        clock.start();return;
                    }
                    var hrs=getNumberInput(clock.eHours);
                    if(isNaN(hrs)){
                        return;
                    }
                    var mins=getNumberInput(clock.eMinutes);
                    if(isNaN(mins)){
                        return;
                    }
                    var secs=getNumberInput(clock.eSeconds);
                    if(isNaN(secs)){
                        return;
                    }
                    if(hrs+mins+secs==0){
                        alert($UMSG_NO_INPUT);
                        return;
                    }
                    if(mins>59){
                        alert($UMSG_INVALID_MINUTES);
                        clock.eMinutes.focus();
                        return;
                    }if(secs>59){
                        alert($UMSG_INVALID_SECONDS);clock.eSeconds.focus();
                        return;
                    }
                    clock.eStart.disabled=true;
                    clock.ePause.disabled=false;
                    clock.eReset.disabled=false;
                    if(event[TARGET]==clock.eStart){
                        clock.eReset.focus();
                    }clock.disableTf(true);
                    //clock.start(hrs*3600+mins*60+secs);
                    break;
            }
        }
        addListener(clock.eStart,"click",handler);
        addListener(clock.ePause,"click",handler);
        addListener(clock.eResume,"click",handler);
        addListener(clock.eReset,"click",handler);
    }
    function getElement(id){
        console.log(id);
            return document.getElementById(clockType+number+"_"+id);
        }
    function initClock(clockType,number,intervalo){   

        function getElement(id){
            return document.getElementById(clockType+number+"_"+id);
        }       
 
        var clock=new Clock();
        clockList[clockList.length]=clock;
        function Clock(){
           
            this.type=clockType;
            this.eCont = document.getElementById(clockType+number);

            this.eDigits=getElement("digits");
            this.eStart=getElement("start");
            this.ePause=getElement("pause");
            this.eResume=getElement("resume");
            this.eReset=getElement("reset");

            if(clockType==COUNT_DOWN){
              
                this.eHours=getElement("hours");               
              
                this.eSeconds=getElement("seconds");
               
                this.eMinutes=getElement("minutes");
                this.eHours.maxLength=this.eMinutes.maxLength=this.eSeconds.maxLength=2;

               function handler(event){
                    if(String.fromCharCode(event.keyCode)=="\r"){
                        clock.eStart.click();
                        if(TARGET==DOM_TARGET){
                            return false;
                        }else{
                            event.returnValue=false;
                        }
                    }
                }
                addListener(this.eHours,"keydown",handler);addListener(this.eMinutes,"keydown",handler);addListener(this.eSeconds,"keydown",handler);
            }
            this.cTicks=0;
            this.eDigits.innerHTML="00:00:00";
            Clock.ticker=function(){
       
                if(clock.type==COUNT_UP){
                    ++clock.cTicks;clock.showTime();
                }else{
                    clock.showTime();                  
            
                    if(clock.cTicks){
                        --clock.cTicks;
                    }else{
                      
                        clock.stop();
                        clock.ePause.disabled=true;
                        clock.eResume.disabled=true;
                        clock.eReset.disabled=true;
                        window.focus();
                        if(window.showModalDialog&&window.attachEvent){
                            showModalDialog(DLG_URL,clockList,IE_DLG_STYLE);
                        }else{
                            //alert("TERMINO!!!");
                            //window.open(DLG_URL,"",GK_DLG_STYLE).focus();
                        }
                        clock.eReset.disabled=false;
                        resetHandler(clock);clock.eReset.click();
                    }
                }
            };
            this.showTime=function(){
                if(this.cTicks<=MAX_TICKS){
                    this.eDigits.innerHTML=Math.floor(this.cTicks/3600/10)+""+Math.floor(this.cTicks/3600%10)+":"+Math.floor(this.cTicks%3600/60/10)+""+Math.floor(this.cTicks%3600/60%10)+":"+Math.floor(this.cTicks%3600%60/10)+""+this.cTicks%3600%60%10;
                    //document.title=this.eDigits.innerHTML+" "+ORIG_TITLE;
                }
            };
            this.disableTf=function(flag){
                this.eHours.disabled=this.eMinutes.disabled=this.eSeconds.disabled=flag;
            };
            this.start=function(newCountDownFrom){
                if(newCountDownFrom){
                    this.cTicks=newCountDownFrom;
                    this.showTime();
                    --this.cTicks;
                }this.intervalID=setInterval(Clock.ticker,1000);
            };
            this.stop=function(){
                clearInterval(this.intervalID);this.intervalID=null;
            };
            this.reset=function(){
                this.stop();this.cTicks=0;this.showTime();
            };
            this.isActive=function(){
                return this.intervalID!=null;
            };
            if(this.type==COUNT_DOWN){
                this.disableTf(false);
            }
            this.eStart.disabled=false;
            this.ePause.disabled=true;
            this.eResume.disabled=true;
            this.eReset.disabled=true;
            initHandlers(this);
        }
       
        clock.start(intervalo);
        return true;
    }   
    
    initClock(COUNT_DOWN,1,timeToEnd);
  
}