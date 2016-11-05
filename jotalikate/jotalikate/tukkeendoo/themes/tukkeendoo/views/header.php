<!DOCTYPE html>
<html>
	<body>                                      
	     <?php 
	        $this->CI =& get_instance();
	        $carpool_session['carpool_session']   = $this->CI->carpool_session->userdata('carpool');
	        //print_r($carpool_session['carpool_session'] );
	         $id  = $carpool_session['carpool_session']['user_id'];
	         $profile = $this->auth_travel->get_travel($id);
	        if($this->auth_travel->is_logged_in(false, false)):  
				$profile = $profile;
			endif;     
	        ?>  
