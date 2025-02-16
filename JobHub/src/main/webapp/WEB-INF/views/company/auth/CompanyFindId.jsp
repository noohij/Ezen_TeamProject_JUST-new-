<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JobHub : 아이디 찾기</title>
<style type="text/css">
	#container {
		margin: 0px auto;
		padding: 135px 270px;
		width: 540px;
		background-color: #eff5ff;
	}
	#findIdBox {
		margin: 0px auto;
		width: 540px;
		height: 640px;
		background-color: #fff;
		border-radius: 10px;
		box-shadow: 0px 1px 4px 0px #ddd;
		text-align: center;
	}
	#contentBox {
		margin: 0px auto;
		padding: 20px 0px;
		width: 400px;
		height: 630px;
		text-align: left;
	}
	#titleBox > p {
	    font-size: 21px;
    	font-weight: bold;
	}
	.inputBox {
		float: left;
		width: 400px;
		height: 100px;
	}
	.inputBox > input {
		width: 250px;
		height: 35px;
		margin-right: 10px;
	    border: 1px solid #d7dce5;
	    border-radius: 6px;
	}
	
	.dummyBox{
		display: none;
	}
	
	#buttonBox {
		width: 400px;
		height: 100px;
		margin-top: 30px;
		float: left;
	}
	
	button {
	    width: 115px;
	    height: 38px;
	    background-color: #4876ef;
	    border: none;
	    border-radius: 12px;
	    box-shadow: 0px 1px 4px 0px #ddd;
	    font-size: 16px;
	    font-weight: bold;
	    color: #fff;
	}
	
</style>
<script type="text/javascript">
	
	var codeValue = 0;
	
	function findIdFnc() {
		var idSearch = document.getElementById("findIdBox");
		
		if(idSearch.comInChargeName.value == "") {
			alert("이름을 입력해주세요.");
			return false;
		}
		
		if (idSearch.comInChargeEmail.value  == "") {
			alert("이메일을 입력해주세요.");
			return false;
		}
		
		var codeNum = Math.random().toString().substr(2,6);
		alert(codeNum);
		codeValue = codeNum;
	}
	
	function codeCheck() {
		var codeCheck = document.getElementById("findIdBox");
		
		if(codeCheck.code.value != codeValue){
			alert("인증번호가 일치하지 않습니다.");
			return false;
		} else if(codeCheck.code.value == codeValue){
			return true;
		}
	}
	
</script>
</head>
<body>

	<div id="container">
		<form id="findIdBox" action="./findIdCtr.do" method="post" onsubmit="return codeCheck();">
			<div id="contentBox">
				<div id="titleBox">
					<p>아이디 찾기</p>
				</div>
				<div>
					<div id="nameBox" class="inputBox">
						<p>담당자 이름</p>
						<input name="comInChargeName" type="text">
					</div>
					<div id="emailBox" class="inputBox">
						<p>담당자 이메일 주소</p>
						<input name="comInChargeEmail" type="text">
						<button type="button" onclick="findIdFnc();">인증번호 받기</button>
					</div>
				</div>
				<div id="validationBox" class="inputBox">
					<p>인증번호</p>
					<input name="code" type="text" placeholder="인증번호 6자리 숫자 입력">
					<button type="submit">인증번호 확인</button>
				</div>
				<div class="inputBox">
					<p class="dummyBox" />
					<input class="dummyBox">
				</div>
				<div id="buttonBox">
					<button type="button" onclick="location.href='./login.do';">돌아가기</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>