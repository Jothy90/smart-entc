<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from bucketadmin.themebucket.net/blank.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 18 Oct 2014 09:20:35 GMT -->
<head>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="images/favicon.html">

    <title>ENTC</title>

    <!--Core CSS -->
    <link href="resources/bs3/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap-reset.css" rel="stylesheet">
    <link href="resources/font-awesome/css/font-awesome.css" rel="stylesheet"/>

    <!-- Custom styles for this template -->
    <link href="resources/css/style.css" rel="stylesheet">
    <link href="resources/css/style-responsive.css" rel="stylesheet"/>

    <!--clock css-->
    <link href="resources/js/css3clock/css/style.css" rel="stylesheet">

    <%--<!-- Custom styles for counter template -->
    <link href="resources/counter/css/soon.css" rel="stylesheet">

    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'>
--%>
    <%--Google pie chart for weather meter--%>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type='text/javascript'>
        google.load('visualization', '1', {packages: ['gauge']});
        google.setOnLoadCallback(drawChart);
        var data;
        var options;
        var chart;
        var data1;
        var options1;
        var chart1;
        function drawChart() {
            data = google.visualization.arrayToDataTable([
                ['Label', 'Value'],
                ['\'C', 33]

            ]);

            options = {
                min: 0, max: 50,
                width: 500, height: 165,
                greenFrom: 20, greenTo: 35,
                redFrom: 35, redTo: 50,
                yellowFrom: 0, yellowTo: 20,
                minorTicks: 3
            };

            chart = new google.visualization.Gauge(document.getElementById('temp_meter'));
            chart.draw(data, options);

            data1 = google.visualization.arrayToDataTable([
                ['Label', 'Value'],
                ['%', 40]

            ]);

            options1 = {
                min: 0, max: 100,
                width: 500, height: 165,
                redFrom: 90, redTo: 100,
                yellowFrom: 45, yellowTo: 90,
                greenFrom: 0, greenTo: 45,
                minorTicks: 5
            };

            chart1 = new google.visualization.Gauge(document.getElementById('hum_meter'));
            chart1.draw(data1, options1);
        }
    </script>

    <%--MQTT Subscriber--%>
    <script src="resources/js/mqttws31.js" type="text/javascript"></script>
    <script type="text/javascript">
        //settings BEGIN
        var MQTTbroker = '192.248.10.70';  //'messagesight.demos.ibm.com';
        var MQTTport = 8000;
        var MQTTsubTopic = 'Server/${node.name}'; //works with wildcard # and + topics dynamically now
        var noOfPeople = 5;
        //settings END

        var dataTopics = new Array();

        //mqtt broker
        var client = new Paho.MQTT.Client(MQTTbroker, MQTTport,
                "myclientid_" + parseInt(Math.random() * 100, 10));
        client.onMessageArrived = onMessageArrived;
        client.onConnectionLost = onConnectionLost;
        //connect to broker is at the bottom of the init() function !!!!


        //mqtt connecton options including the mqtt broker subscriptions
        var options_mqtt = {
            timeout: 3,
            onSuccess: function () {
                console.log("mqtt connected");
                // Connection succeeded; subscribe to our topics
                client.subscribe(MQTTsubTopic, {qos: 1});
            },
            onFailure: function (message) {
                console.log("Connection failed, ERROR: " + message.errorMessage);
                //window.setTimeout(location.reload(),20000); //wait 20seconds before trying to connect again.
            }
        };

        //can be used to reconnect on connection lost
        function onConnectionLost(responseObject) {
            console.log("connection lost: " + responseObject.errorMessage);
            window.setTimeout(location.reload(), 20000); //wait 20seconds before trying to connect again.
        }
        ;

        //what is done when a message arrives from the broker
        function onMessageArrived(message) {
            console.log(message.destinationName, '', message.payloadString);

            var readings = message.payloadString.split(' ');
            for (x = 0; x < readings.length; x++) {
                var reading = readings[x].split(':');
                switch (parseInt(reading[0])) {
                    case 1:
                        data = google.visualization.arrayToDataTable([
                            ['Label', 'Value'],
                            ['\'C', parseInt(reading[1])]

                        ]);
                        chart.draw(data, options);
                        break;
                    case 2:
                        data1 = google.visualization.arrayToDataTable([
                            ['Label', 'Value'],
                            ['%', parseInt(reading[1])]

                        ]);
                        chart1.draw(data1, options1);
                        break;
                    case 3:
                        break;
                    case 4:
                        noOfPeople = noOfPeople + parseInt(reading[1]);
                        var peopleCount = $('#peopleCount');
                        peopleCount.html(("00" + noOfPeople).slice(-3) + '<span>Peoples</span>');
                        break;
                    case 5:
                        break;
                }
            }
            var activity = $('#activity');
            activity.html('<span>Lecture Out</span>No activity');

        }
        ;

        function init() {
            // Connect to MQTT broker
            client.connect(options_mqtt);

            var peopleCount = $('#peopleCount');
            peopleCount.html(("00" + ${node.peopleCount}).slice(-3) + '<span>Peoples</span>');

            var activity = $('#activity');
            switch (${node.activity}) {
                case 0:
                    activity.html('<span>No Students</span>No activity');
                    break;
                case 1:
                    activity.html('<span>Students are presence</span>No activity');
                    break;
                case 2:
                    activity.html('<span>Lecture is Going</span>Activity Happening');
                    break;
            }

        }

        //check if a real number
        function isNumber(n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        }
        ;
    </script>
</head>

<body onload="init()">

<section id="container">
<!--header start-->
<header class="header fixed-top clearfix">
    <!--logo start-->
    <div class="brand">

        <a href="home" class="logo">
            <img src="resources/images/ENTC.png" alt="">
        </a>

        <div class="sidebar-toggle-box">
            <div class="fa fa-bars"></div>
        </div>
    </div>
    <!--logo end-->

    <div class="nav notify-row" id="top_menu">
        <!--  notification start -->
        <ul class="nav top-menu">
            <!-- notification dropdown start-->
            <li id="header_notification_bar" class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">

                    <i class="fa fa-bell-o"></i>
                    <span class="badge bg-important">3</span>
                </a>
                <ul class="dropdown-menu extended notification">
                    <li>
                        <p>Notifications</p>
                    </li>
                    <li>
                        <div class="alert alert-info clearfix">
                            <span class="alert-icon"><i class="fa fa-bolt"></i></span>

                            <div class="noti-info">
                                <a href="#"> ENTC-1 Lights ON No One There.</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="alert alert-danger clearfix">
                            <span class="alert-icon"><i class="fa fa-bolt"></i></span>

                            <div class="noti-info">
                                <a href="#"> Seminar Room AC Working No One There.</a>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="alert alert-success clearfix">
                            <span class="alert-icon"><i class="fa fa-bolt"></i></span>

                            <div class="noti-info">
                                <a href="#"> Analog Lab CO2 level Higher than Accept Level.</a>
                            </div>
                        </div>
                    </li>

                </ul>
            </li>
            <!-- notification dropdown end -->
        </ul>
        <!--  notification end -->
    </div>
    <div class="top-nav clearfix">
        <!--search & user info start-->
        <ul class="nav pull-right top-menu">
            <!-- user login dropdown start-->
            <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle icon-user" href="#">
                    <!--<img alt="" src="images/avatar1_small.jpg">-->
                    <i class="fa fa-user"></i>
                    <span class="username">${userName}</span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu extended logout">
                    <li><a href="#"><i class=" fa fa-suitcase"></i>Profile</a></li>
                    <li><a href="#"><i class="fa fa-cog"></i> Settings</a></li>
                    <li><a href="j_spring_security_logout"><i class="fa fa-key"></i> Log Out</a></li>
                </ul>
            </li>
            <!-- user login dropdown end -->
        </ul>
        <!--search & user info end-->
    </div>
</header>
<!--header end-->
<aside>
    <div id="sidebar" class="nav-collapse">
        <!-- sidebar menu start-->
        <div class="leftside-navigation">
            <ul class="sidebar-menu" id="nav-accordion">
                <li>
                    <a href="home">
                        <i class="fa fa-dashboard"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li class="sub-menu">
                    <a href="info?location=-0.5">
                        <i class="fa fa-user"></i>
                        <span>-0.5</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=Bio">Bio lab</a></li>
                        <li><a href="info?location=Study.5">Study hall</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-laptop"></i>
                        <span>Ground Floor</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=ENTC1">ENTC1</a></li>
                        <li><a href="info?location=UUV">UUV Lab</a></li>
                    </ul>
                </li>
                <li>
                    <a href="info?location=-0.5">
                        <i class="fa fa-bar-chart-o"></i>
                        <span>+0.5</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=Research">Research Lab</a></li>
                        <li><a href="info?location=Study">Study Hall</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-book"></i>
                        <span>1st Floor</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=Madam">Dileeka Madam's Room</a></li>
                        <li><a href="info?location=RangaSir">Ranga Sir's Room</a></li>
                        <li><a href="info?location=JayasingeSir">Jayasinge Sir's Room</a></li>
                    </ul>
                </li>

                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-th"></i>
                        <span>2nd Floor</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=Digital">Digital Lab</a></li>
                        <li><a href="info?location=Work">Workshop</a></li>
                        <li><a href="info?location=Instructor">Instructor Room</a></li>
                        <li><a href="info?location=RohanSir">Rohan Sir's Room</a></li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:;">
                        <i class="fa fa-tasks"></i>
                        <span>3rd Floor</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=Telecom">Telecom Lab</a></li>
                        <li><a href="info?location=Dialog">Dialog Lab</a></li>
                        <li><a href="info?location=PG">PG Seminar</a></li>
                        <li><a href="info?location=Micro">Micro Wave</a></li>
                    </ul>
                </li>
                <li>
                    <a href="info?location=-0.5">
                        <i class="fa fa-glass"></i>
                        <span>3.5</span>
                    </a>
                    <ul class="sub">
                        <li><a href="info?location=Instructors">Instructors Room</a></li>
                        <li><a href="info?location=Lec3.5">Lecture Hall</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- sidebar menu end-->
    </div>
</aside>
<!--sidebar end-->
<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <!-- page start-->

        <div class="row thumbnail">
            <div class="col-sm-7">
                <section class="panel">
                    <div class="panel-body thumbnail">
                        <div class="top-stats-panel thumbnail">
                            <div id="peopleCount" class="mini-stat-info" style="font-size: 80px; text-align: center">
                                No Informations
                                <span>Peoples</span>
                            </div>
                        </div>
                        <div>
                            <div class="col-sm-6" id='temp_meter'></div>
                            <div class="col-sm-6" id='hum_meter'></div>
                            <div class="lead col-sm-6" style="margin-left: 20px;">Temperature</div>
                            <div class="lead col-sm-5 "> Humidity Level</div>
                        </div>
                    </div>
                </section>
            </div>
            <div class="col-sm-5">
                <section class="panel">
                    <div class="panel-body thumbnail">
                        <div class="mini-stat clearfix thumbnail center-block">
                            <span class="mini-stat-icon orange"><i class="fa fa-gavel"></i></span>

                            <div id="activity" class="mini-stat-info">
                                <span>No Information</span>
                                Forbidden
                            </div>
                        </div>
                        <div class="profile-nav alt">
                            <section class="panel">
                                <div class="user-heading alt clock-row terques-bg">
                                    <h1>December 14</h1>

                                    <p class="text-left">2014, Friday</p>

                                    <p class="text-left">7:53 PM</p>
                                </div>
                                <ul id="clock">
                                    <li id="sec"></li>
                                    <li id="hour"></li>
                                    <li id="min"></li>
                                </ul>
                            </section>
                        </div>
                    </div>
                </section>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="mini-stat clearfix">
                    <span class="mini-stat-icon orange"><i class="fa fa-clock-o"></i></span>

                    <div class="mini-stat-info">
                        <span><a href="temperature?location=${node.name}">See</a></span>
                        Temperature
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="mini-stat clearfix">
                    <span class="mini-stat-icon tar"><i class="fa fa-cloud"></i></span>

                    <div class="mini-stat-info">
                        <span><a href="humidity?location=${node.name}">See</a></span>
                        Humidity Variations
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="mini-stat clearfix">
                    <span class="mini-stat-icon pink"><i class="fa fa-sun-o"></i></span>

                    <div class="mini-stat-info">
                        <span><a href="light?location=${node.name}">See</a></span>
                        Light Variations
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="mini-stat clearfix">
                    <span class="mini-stat-icon green"><i class="fa fa-volume-up"></i></span>

                    <div class="mini-stat-info">
                        <span><a href="noise?location=${node.name}">See</a></span>
                        Noise Variations
                    </div>
                </div>
            </div>
        </div>
        <!-- page end-->
    </section>
</section>
<!--main content end-->
</section>

<!-- Placed js at the end of the document so the pages load faster -->

<!--Core js-->
<script src="resources/js/jquery.js"></script>
<script src="resources/bs3/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="resources/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="resources/js/jquery.scrollTo.min.js"></script>
<script src="resources/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.js"></script>
<script src="resources/js/jquery.nicescroll.js"></script>


<!--common script init for all pages-->
<script src="resources/js/scripts.js"></script>
<!--clock init-->
<script src="resources/js/css3clock/js/css3clock.js"></script>

<script src="resources/js/gauge/gauge.js"></script>


<%--<script type="text/javascript" src="resources/counter/js/modernizr.custom.js"></script>

<script src="resources/counter/js/soon/plugins.js"></script>
<script src="resources/counter/js/soon/jquery.themepunch.revolution.min.js"></script>
<script src="resources/counter/js/soon/custom.js"></script>--%>

</body>

<!-- Mirrored from bucketadmin.themebucket.net/blank.html by HTTrack Website Copier/3.x [XR&CO'2014], Sat, 18 Oct 2014 09:20:35 GMT -->
</html>
