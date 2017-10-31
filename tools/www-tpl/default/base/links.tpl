<div class="nav-bar">
	<div class="container">
		<ul class="nav">
			<li><a href="{{ site.url }}/home">HOME</a></li>
			<li><a href="{{ site.url }}/community">COMMUNITY</a></li>
			<li><a href="{{ site.url }}/staff">STAFF</a></li>
			{% if session.loggedIn %}
				<li><a href="{{ site.url }}}/account/logout">LOGOUT</a></li>
				
				{% if session.housekeeping %}
					<li><a href="{{ site.url }}}/housekeeping">HOUSEKEEPING</a></li>
				{% endif %}
			
			{% endif %}
	</div>
</div>