<div style="
		border: 1px solid #aab;
		background-color: #fafafa;
		-moz-box-shadow: 2px 2px 2px #eee;
		-webkit-box-shadow: 2px 2px 2px #eee;
		-khtml-box-shadow: 2px 2px 2px #eee;
		box-shadow: 2px 2px 2px #eee;">
		
<div class='fheader'><g:message code="springSecurity.login.header"/></div>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

		<form action='/j_spring_security_check' method='POST' id='loginForm' class='cssform' autocomplete='off' >
			<p>
				<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input id="usernameInput" type='text' class='text_' name='j_username' id='username' focus-me="true"/>
			</p>

			<p>
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</p>

			<p>
				<label for='remember_me' style="width:150px;margin-left:0px"><g:message code="springSecurity.login.remember.me.label"/>:</label>
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
			</p>

			<p>
				<input type='submit' id="submit" style="margin-left:100px" value='${message(code: "springSecurity.login.button")}'/>
				<input type='reset' id="reset" value='${message(code: "form.reset")}'/>
			</p>
			
<p class="oauth-button-container" style="padding-left:150px;border-top:1px dotted black;">
    <b>OR</b>
    <br/>
    <br/>
    <label>Log in with:</label>
    <oauth:connect provider="facebook" id="facebook-connect-link">
      <img src="${resource(dir: 'images/app/auth', file: 'facebook_icon.png')}" alt="Facebook" width="25px" height="25px"/>
    </oauth:connect>
    <oauth:connect provider="google" id="google-connect-link">
      <img src="${resource(dir: 'images/app/auth', file: 'google_icon.png')}" alt="Google" width="25px" height="25px"/>
    </oauth:connect>
    <oauth:connect provider="twitter" id="twitter-connect-link">
      <img src="${resource(dir: 'images/app/auth', file: 'twitter_icon.png')}" alt="Twitter" width="25px" height="25px"/>
    </oauth:connect>
</p>
	</form>
</div>