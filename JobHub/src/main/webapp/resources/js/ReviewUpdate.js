function titleFocusFnc() {
		var titleObj = document.getElementById("titleInput");
		
			titleObj.setAttribute("style", "outline: 2px solid #94b5fc");
	}

	function titleBlurFnc() {
		var titleObj = document.getElementById("titleInput");
		var titleErrorMsgObj = document.getElementById("titleErrorMsg")
		
		if (titleObj.value == "" || titleObj.value.length < 20) {
			titleObj.setAttribute("style", "border: 2px solid #d7dce5");
			titleErrorMsgObj.setAttribute("style", "display: none")
		} else if (titleObj.value.length > 20){
			titleObj.setAttribute("style", "border: 2px solid red");
			titleErrorMsgObj.setAttribute("style", "display: block")
		}
	}
	
	function contentFocusFnc() {
		var contentObj = document.getElementById("contentInput");
		
			contentObj.setAttribute("style", "outline: 2px solid #94b5fc")
	}
	
	function contentBlurFnc() {
		var contentObj = document.getElementById("contentInput");
		var contentErrorMsgObj = document.getElementById("contentErrorMsg")
		
		if (contentObj.value == "" || contentObj.value.length < 200) {
			contentObj.setAttribute("style", "border: 2px solid #d7dce5");
			contentErrorMsgObj.setAttribute("style", "display: none")
		} else {
			contentObj.setAttribute("style", "border: 2px solid red");
			contentErrorMsgObj.setAttribute("style", "display: block")
		}
	}
	
	function onSubmitFnc(event) {
		
		// 제목 유효성 검사
		var titleObj = document.getElementById("titleInput");
		var titleErrorMsgObj = document.getElementById("titleErrorMsg")
		
		if (titleObj.value.length > 20 || titleObj.value.length < 1){
			titleObj.setAttribute("style", "border: 2px solid red");
			titleErrorMsgObj.setAttribute("style", "display: block");
			event.preventDefault();
		} else if (titleObj.value != "" && titleObj.value.length < 20) {
			titleObj.setAttribute("style", "border: 1px solid #d7dce5");
			titleErrorMsgObj.setAttribute("style", "display: none");
		}
		
		// 내용 유효성 검사
		var contentObj = document.getElementById("contentInput");
		var contentErrorMsgObj = document.getElementById("contentErrorMsg")
		
		if (contentObj.value == "" || contentObj.value.length > 200) {
			contentObj.setAttribute("style", "border: 2px solid red");
			contentErrorMsgObj.setAttribute("style", "display: block");
			event.preventDefault();
		} else if (contentObj.value == "" && contentObj.value.length < 200) {
			contentObj.setAttribute("style", "border: 1px solid #d7dce5");
			contentErrorMsgObj.setAttribute("style", "display: none");
		}
		
		// 별점 유효성검사
		var salRateList = document.getElementsByName("reviewSal");
        var welRateList = document.getElementsByName("reviewWel");
        var envRateList = document.getElementsByName("reviewEnv");
        var bossRateList = document.getElementsByName("reviewBoss");
        var balRateList = document.getElementsByName("reviewBal");
        var starInputDivObj = document.getElementById("starInputBox");
        var starErrorMsgObj = document.getElementById("rateErrorMsg");
		
        var checkCnt = 0;

        for (var i = 0; i < salRateList.length; i++) {
            if (salRateList[i].checked) {
                checkCnt++;
            }
        }
        for (var i = 0; i < welRateList.length; i++) {
            if (welRateList[i].checked) {
                checkCnt++;
            }
        }
        for (var i = 0; i < envRateList.length; i++) {
            if (envRateList[i].checked) {
                checkCnt++;
            }
        }
        for (var i = 0; i < bossRateList.length; i++) {
            if (bossRateList[i].checked) {
                checkCnt++;
            }
        }
        for (var i = 0; i < balRateList.length; i++) {
            if (balRateList[i].checked) {
                checkCnt++;
            }
        }
        if (checkCnt < 5) {	// 별점이 빠짐없이 체크되었다면 checkCnt는 5가 되어야만 한다.
        	starInputDivObj.setAttribute("style", "border: 2px solid red");
        	starErrorMsgObj.setAttribute("style", "display: block");
            event.preventDefault(); // 별점이 5개 중 하나라도 선택되지 않았을 때 폼 제출 방지
        } else if (checkCnt = 5) {
	    	starInputDivObj.setAttribute("style", "border: 1px solid #d7dce5");
	    	starErrorMsgObj.setAttribute("style", "display: none");
	   	 }
		
	    var rAvgResultInputObj = document.getElementById("rAvgResult");
	
	    var totalRateNum = 0;
	
	    for (var i = 0; i < salRateList.length; i++) {
	        if (salRateList[i].checked) {
	            totalRateNum += parseFloat(salRateList[i].value);
	        }
	    }
	
	    for (var i = 0; i < welRateList.length; i++) {
	        if (welRateList[i].checked) {
	            totalRateNum += parseFloat(welRateList[i].value);
	        }
	    }
	
	    for (var i = 0; i < envRateList.length; i++) {
	        if (envRateList[i].checked) {
	            totalRateNum += parseFloat(envRateList[i].value);
	        }
	    }
	
	    for (var i = 0; i < bossRateList.length; i++) {
	        if (bossRateList[i].checked) {
	            totalRateNum += parseFloat(bossRateList[i].value);
	        }
	    }
	
	    for (var i = 0; i < balRateList.length; i++) {
	        if (balRateList[i].checked) {
	            totalRateNum += parseFloat(balRateList[i].value);
	        }
	    }
	    rAvgResultInputObj.value = (totalRateNum/5);
	}