<html>
<head>
	<meta name='layout' content='main'/>
	<title><g:message code="springSecurity.login.title"/></title>
	
    <script src="${resource(dir: 'js', file: 'dynamix-auth.js')}"></script>	
  		
	<style type='text/css' media='screen'>
	#footerOauthImages{
		display:none;
	}
	
	#login {
		margin: 15px 0px;
		padding: 0px;
		text-align: center;
	}

	#login .inner {
		width: 340px;
		padding-bottom: 6px;
		margin: 60px auto;
		text-align: left;
	}

	#login .inner .fheader {
		padding: 18px 26px 14px 26px;
		background-color: #eee;
		margin: 0px 0 14px 0;
		color: #2e3741;
		font-size: 18px;
		font-weight: bold;
	}

	#login .inner .cssform p {
		clear: left;
		margin: 0;
		padding: 4px 0 3px 0;
		padding-left: 105px;
		margin-bottom: 20px;
		height: 1%;
	}

	#login .inner .cssform input[type='text'] {
		width: 150px;
	}

	#login .inner .cssform label {
		font-weight: bold;
		float: left;
		text-align: right;
		margin-left: -105px;
		width: 110px;
		padding-top: 3px;
		padding-right: 10px;
	}

	#login #remember_me_holder {
		padding-left: 75px;
	}

	#login #submit {
		margin-left: 15px;
	}

	#login #remember_me_holder label {
		float: none;
		margin-left: 0;
		text-align: left;
		width: 200px
	}

	#login .inner .login_message {
		padding: 6px 25px 20px 25px;
		color: #c33;
	}

	#login .inner .text_ {
		width: 150px;
	}

	#login .inner .chk {
		height: 12px;
	}
	
	.main-body{
	 	min-height: 310px;
	}
	
@media screen and (max-width: 25em) {
	#login .inner {
		margin: 30px auto;
	}
	.main-body{
	 	min-height: 290px;
	}
}
	
	</style>
</head>

<body>
<div id='login'>
<div class="view-container main-body">
	<div class='inner' ng-controller="LoginCtrl">
		<div class="slide-reveal" ng-include="template.url"/>
	</div>
	</div>
</div>
</body>
</html>
