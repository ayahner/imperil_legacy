  <div class="footer" role="contentinfo">
    <div id="footerOauthImages" class="footer-oauth-images">
      <s2o:ifLoggedInWith provider="facebook">
        <img
          src="${resource(dir: 'images/app/auth', file: 'facebook_icon.png')}"
          alt="Facebook" width="25px" height="25px">
      </s2o:ifLoggedInWith>
      <s2o:ifNotLoggedInWith provider="facebook">
        <img class="desaturate"
          src="${resource(dir: 'images/app/auth', file: 'facebook_icon.png')}"
          alt="Facebook" width="25px" height="25px">
      </s2o:ifNotLoggedInWith>

      <s2o:ifLoggedInWith provider="google">
        <img
          src="${resource(dir: 'images/app/auth', file: 'google_icon.png')}"
          alt="google" width="25px" height="25px">
      </s2o:ifLoggedInWith>
      <s2o:ifNotLoggedInWith provider="google">
        <img class="desaturate"
          src="${resource(dir: 'images/app/auth', file: 'google_icon.png')}"
          alt="google" width="25px" height="25px">
      </s2o:ifNotLoggedInWith>

      <s2o:ifLoggedInWith provider="twitter">
        <img
          src="${resource(dir: 'images/app/auth', file: 'twitter_icon.png')}"
          alt="twitter" width="25px" height="25px">
      </s2o:ifLoggedInWith>
      <s2o:ifNotLoggedInWith provider="twitter">
        <img class="desaturate"
          src="${resource(dir: 'images/app/auth', file: 'twitter_icon.png')}"
          alt="twitter" width="25px" height="25px">
      </s2o:ifNotLoggedInWith>
    </div>
    <g:message code="application.title" />
    &copy;2014&nbsp;&nbsp;
  </div>
