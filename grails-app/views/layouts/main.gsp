<!DOCTYPE html>
<!--  test -->
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6" ng-app> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7" ng-app> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8" ng-app> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9" ng-app> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js" ng-app="imperilApp">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title><g:layoutTitle default="Application" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="A turn based strategy game of world domination.">
<meta name="keywords"
	content="MESOTHELIOMA LAW FIRM, DONATE CAR TO CHARITY CALIFORNIA, DONATE CAR FOR TAX CREDIT, DONATE CARS IN MA, DONATE YOUR CAR SACRAMENTO, HOW TO DONATE A CAR IN CALIFORNIA,  SELL ANNUITY PAYMENT, DONATE YOUR CAR FOR KIDS, ASBESTOS LAWYERS, STRUCTURED ANNUITY SETTLEMENT, ANNUITY SETTLEMENTS, CAR INSURANCE QUOTES COLORADO, NUNAVUT CULTURE, DAYTON FREIGHT LINES, HARDDRIVE DATA RECOVERY SERVICES, DONATE A CAR IN MARYLAND, MOTOR REPLACEMENTS, CHEAP DOMAIN REGISTRATION HOSTING, DONATING A CAR IN MARYLAND, DONATE CARS ILLINOIS, CRIMINAL DEFENSE ATTORNEYS FLORIDA, BEST CRIMINAL LAWYER IN ARIZONA, LIFE INSURANCE CO LINCOLN, HOLLAND MICHIGAN COLLEGE, ONLINE MOTOR INSURANCE QUOTES, ONLINE COLLEDGES, PAPERPORT PROMOTIONAL CODE, ONLINECLASSES, WORLD TRADE CENTER FOOTAGE, MASSAGE SCHOOL DALLAS TEXAS, PSYCHIC FOR FREE, DONATE OLD CARS TO CHARITY, LOW CREDIT LINE CREDIT CARDS, DALLAS MESOTHELIOMA ATTORNEYS, CAR INSURANCE QUOTES MN, DONATE YOUR CAR FOR MONEY, CHEAP AUTO INSURANCE IN VA, MET AUTO, FORENSICS ONLINE COURSE, HOME PHONE INTERNET BUNDLE, DONATING USED CARS TO CHARITY, PHD IN COUNSELING EDUCATION, NEUSON, CAR INSURANCE QUOTES PA, ROYALTY FREE IMAGES STOCK, CAR INSURANCE IN SOUTH DAKOTA, EMAIL BULK SERVICE, WEBEX COSTS, CHEAP CAR INSURANCE FOR LADIES, CHEAP CAR INSURANCE IN VIRGINIA, REGISTER FREE DOMAINS, BETTER CONFERENCING CALLS, FUTURISTIC ARCHITECTURE, MORTGAGE ADVISER,  CAR DONATE, VIRTUAL DATA ROOMS, AUTOMOBILE ACCIDENT ATTORNEY, AUTO ACCIDENT ATTORNEY, CAR ACCIDENT LAWYERS, DATA RECOVERY RAID, MOTOR INSURANCE QUOTES, PERSONAL INJURY LAWYER, CAR INSURANCE QUOTES, ASBESTOS LUNG CANCER, INJURY LAWYERS, PERSONAL INJURY LAW FIRM, ONLINE CRIMINAL JUSTICE DEGREE, CAR INSURANCE COMPANIES, BUSINESS VOIP SOLUTIONS">

<link rel="icon" type="image/png" href="${resource(dir: 'images', file: 'favicon.ico')}" />

<r:layoutResources />
<g:layoutHead />
<script src="/semantic/javascript/semantic.js"></script>
<link rel="stylesheet" href="/semantic/css/semantic.css" />
<script src="/js/angular-file-upload.js"></script>
<script src="/js/Application.js"></script>
<script src="/js/MapUtils.js"></script>
<script src="/js/Config.js"></script>
<script src="/js/Directive.js"></script>
<script src="/js/Service.js"></script>
<script src="/js/controller/MainController.js"></script>
<script src="/js/controller/HomeController.js"></script>
<script src="/js/controller/MatchController.js"></script>
<script src="/js/controller/MatchBoardMapController.js"></script>
<script src="/js/controller/EditBoardMapController.js"></script>
<script src="/js/controller/UploadController.js"></script>
<script src="/js/controller/CreateMatchController.js"></script>
<script src="/js/controller/CreateBoardMapController.js"></script>
<script>
  (function(i, s, o, g, r, a, m) {
    i['GoogleAnalyticsObject'] = r;
    i[r] = i[r] || function() {
      (i[r].q = i[r].q || []).push(arguments)
    }, i[r].l = 1 * new Date();
    a = s.createElement(o), m = s.getElementsByTagName(o)[0];
    a.async = 1;
    a.src = g;
    m.parentNode.insertBefore(a, m)
  })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

  ga('create', 'UA-51719353-1', 'imperil.herokuapp.com');
  ga('send', 'pageview');
</script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhH_BZfn7xYp224T4NosfkmOa50WDfmZs&libraries=drawing"></script>
<link rel="stylesheet" href="/css/Application.css" />
</head>
<body>
	<div class="ui segment">
		<!-- remove segment? -->
		<h1 class="ui left floated header">Imperil</h1>

		<img class="ui right floated image" src="${resource(dir: 'images/imperil', file: 'imperil_logo.png')}" alt="Imperil">
	</div>
	<div>
		<g:layoutBody />
	</div>
	<r:layoutResources />
</body>
</html>
