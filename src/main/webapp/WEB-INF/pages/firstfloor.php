<?php
/* @var $this SiteController */

$this->pageTitle=Yii::app()->name;
?>

<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<!-- page start-->
		<div class="row">
			<div class="col-sm-12">
				<section class="panel">
					<div class="panel-body thumbnail">
						<img src="images/1st.png" width="715" height="506" border="0" usemap="#map"/>

						<map name="map">
							<area shape="poly" title="Computer Lab"
								  coords="20,270,99,189,225,311,189,343,172,326,118,373,119,372" alt="John"
								  target="c-lab" onmouseover="jmor()" onmouseout="jmot()" onfocus="jof()"
								  onblur="job()" href="http"/>
							<area shape="poly" title="Dayavansa madam's Room"
								  coords="229,372,262,371,262,434,228,433" target="madam-room" onmouseover="mmor"
								  onmouseout="mmot" href="http"/>
							<area shape="poly" title="Dileeka madam's Room" coords="266,372,295,372,297,431,263,434"
								  target="dm-room" href="http"/>
							<area shape="poly" coords="301,372,330,373,333,432,300,433,300,411" target="romm-1"
								  href="http"/>
							<area shape="poly" title="Jayasinge Sir's Room"
								  coords="337,373,354,372,368,383,369,432,336,433" target="room2" href="http"/>
							<area shape="poly" title="1" coords="299,174,330,176,331,207,322,218,300,221"
								  target="kk" href="http"/>
							<area shape="poly" title="2" coords="336,174,365,176,368,220,344,220,337,210,336,208"
								  target="jj" href="http"/>
							<area shape="poly" title="Academic Staff common room"
								  coords="498,115,581,32,618,64,620,93,560,94,544,109,546,150,529,150" target="ac"
								  href="http"/>
							<area shape="poly" title="Conference Room"
								  coords="547,132,548,109,563,100,649,100,650,164,560,166,547,153" target="co"
								  href="http"/>
							<area shape="poly" title="Department Office "
								  coords="517,241,558,200,571,216,582,210,612,238,597,255,565,224,562,227,615,274,583,309"
								  target="do" href="http"/>
							<area shape="poly" title="HOD Office"
								  coords="558,199,557,184,650,187,652,238,635,260,614,248,620,238,585,204,574,214"
								  target="jj" href="http://"/>
						</map>
					</div>
				</section>
			</div>
		</div>
		<!-- page end-->
	</section>
</section>
<!--main content end-->

<!--<script type="text/javascript">
	$('area').qtip({
		content: {
			text: api.elements.target.attr('alt')
		},
		position: {
			my: 'top left',
			at: 'bottom right'
		}
	});
</script>-->