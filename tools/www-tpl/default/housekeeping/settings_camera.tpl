{% include "base/header.tpl" %}
  <body>
    {% set cameraSettingsActive = " active " %}
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Edit Camera Settings</h2>
		{% include "base/alert.tpl" %}
		<p>Here you can edit the variables that the camera uses.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Camera enabled:</i></label>
				<br>
				<select name="camera_enabled">
					<option value="1"{% if cameraenabled == "1" %} selected{% endif %}>Yes</option>
					<option value="0"{% if cameraenabled == "0" %} selected{% endif %}>No</option>
				</select>
			</div>
			<div class="form-group">
				<label>Camera filename:</label>
				<label>
				<small>
					<i>(Use {generatedId} for a random sequence of letters and numbers)</i>
					<br><i>(Use {username} for a the camera owners username)</i>
					<br><i>(Use {id} for the room id the photo was taken)</i>
				</small></label>
				
				<input type="text" class="form-control" id="text" name="camera_filename"  value="{{ camerafilename }}">
			</div>
			<div class="form-group">
				<label>Camera path: <i>(The full directory of where to save the files, this also needs to be accessed for HTTP requests)</i></label>
				<input type="text" class="form-control" id="text" name="camera_path"  value="{{ camerapath }}">
			</div>		
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Save Settings</button>
			</div>
		</form>
        </main>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  </body>
</html>

{% include "base/footer.tpl" %}