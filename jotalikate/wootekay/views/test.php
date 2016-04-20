<?php
$this->load->helper('html');?>

<!DOCTYPE html>
<html>
	<head>
		<?php  $base_url=$this->config->item('base_url'); ?>
		<?php echo theme_js('jquery.min.js', true);?>
	</head>
	
	<body>
		
		<script>
			function get_types(type_id){
			var param = 'tid='+type_id+'&cid=<?php echo $vechiclecategory_id; ?>'
			  $.ajax({
			                type: "POST",
			                url: "<?php echo  $base_url;?>/mobile_app/login/ch",
							data: param,
			      beforeSend: function () {
			   
			      $("#vechicletype").html('Loading.....');
			    },
			                success: function(types)
			                { 
			       $("#vechicletype").html(types);
				   $( "#txtvechicle" ).removeClass('disable');
			      }
			    });
			}
		</script>
		<dive id = "test_content" class ="container">
			
		</dive>
	</body>
</html>