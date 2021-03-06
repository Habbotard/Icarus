{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
	<script type="text/javascript">
	function previewTS(el) {
		document.getElementById('ts-preview').innerHTML = '<img src="{{ site.url }}/c_images/Top_Story_Images/' + el + '" /><br />';
	}
	</script>
      <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Edit Article</h2>
		{% include "base/alert.tpl" %}
		<p>Edit an existing news article that has already been posted on the website.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Title</label>
				<input type="text" class="form-control" name="title" value="{{ article.title }}">
			</div>
			<div class="form-group">
				<label>Short story</label>
				<input type="text" class="form-control" name="shortstory" value="{{ article.shortstory }}">
			</div>
			<div class="form-group">
				<label>Full story</label>
				<p>
					<textarea name="fullstory" class="form-control" rows="6" style="width: 100%;">{{ article.fullstory }}</textarea>
				</p>
			</div>
			<div class="form-group">
				<label>Image</label>
				<p>
					<select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
					{% for image in images %}<option value="{{ image }}"{% if article.topstory == image %} selected{% endif %}>{{ image }}</option>{% endfor %}
					</select>
				</p>
			</div>
			<div class="form-group">
				<label>Image Preview</label>
				<div id="ts-preview"><img src="{{ site.url }}/c_images/Top_Story_Images/{{ article.topstory }}" /></div>
			</div>
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Save Article</button>
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