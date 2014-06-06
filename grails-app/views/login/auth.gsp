<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="springSecurity.login.title" /></title>
<r:require module="common" />
</head>

<body>
	<div class="ui three column grid">
		<div class="column"></div>
		<div class="column">
			<form action='/j_spring_security_check' method='POST'
				class="ui form segment">
				<div class="ui error message"></div>
				<div class="ui inverted dimmer"
					ng-class="{true: 'active', false: 'disabled'}[loading]">
					<div class="ui text loader">Logging in...</div>
				</div>
				<h1>
					<g:message code="springSecurity.login.header" />
				</h1>
				<div class="field">
					<label for="Username">Username</label>
					<div class="ui left labeled icon input">
						<input id="Username" name="j_username" type="text"
							placeholder="Username" ng-model="user.username"> <i
							class="user icon"></i>
						<div class="ui corner label">
							<i class="asterisk red icon"></i>
						</div>
					</div>
				</div>
				<div class="field">
					<label for="Password">Password</label>
					<div class="ui left icon input">
						<input id="Password" name="j_password" type="password"
							ng-model="user.password"> <i class="lock icon"></i>
						<div class="ui corner label">
							<i class="asterisk red icon"></i>
						</div>
					</div>
				</div>
				<div class="ui blue submit button">Login</div>
				<div class="ui right floated icon buttons">
					<oauth:connect class="ui facebook icon button" provider="facebook"
						id="facebook-connect-link">
						<i class="facebook icon"></i>
					</oauth:connect>
					<oauth:connect class="ui google plus icon button"
						provider="google" id="google-connect-link">
						<i class="google plus icon"></i>
					</oauth:connect>
					<oauth:connect class="ui twitter icon button" provider="twitter"
						id="twitter-connect-link">
						<i class="twitter icon"></i>
					</oauth:connect>
				</div>
			</form>
		</div>
	</div>
	<script>
    (function($) {
      $('.ui.form').form({
        username : {
          identifier : 'Username',
          rules : [ {
            type : 'empty',
            prompt : 'Please enter a username'
          } ]
        },
        password : {
          identifier : 'Password',
          rules : [ {
            type : 'empty',
            prompt : 'Please enter a password'
          }, {
            type : 'length[3]',
            prompt : 'Password needs to be atleast 6 characters long'
          } ]
        }
      }, {
        on : 'blur',
        inline : 'true'
      });
    }(jQuery));
  </script>
</body>
</html>
