<?php

class Home extends Front_Controller 
{

    function __construct() 
	{
        parent::__construct();
        $this->load->model('home_model');
        $this->load->model('trip_model');
     	$this->load->library('Auth_travel');
		$this->load->helper('form');
	    $this->load->helper('text');
    }

    function index() 
	{
		$data = array();
		$data['testimonials'] = $this->home_model->get_testimonials($limit = 3);
	    $data['trips'] = $this->trip_model->get_recent_trip($limit = 2);//$this->home_model->get_recently_trip_list($limit = 10, $data);
		if (isset($data)) {
	
			print_r(json_encode($data));
			
			return;
		}
		$response['message'] = "You are not logged in !";
		print(json_encode($response));
    }
	
	function get_recently_trip_list() 
	{
		$isLogin = $this->auth_travel->is_logged_in(false, false);
		if ($isLogin) {
			
			$data = array();
	        $data['trips'] = $this->home_model->get_recently_trip_list($limit = 10);
	
			print_r(json_encode($data));
			
			return;
		}
		$response['message'] = "You are not logged in !";
		print(json_encode($response));
    }
	
	function get_testimonials() 
	{
		$isLogin = $this->auth_travel->is_logged_in(false, false);
		if ($isLogin) {
	        $data['testimonials'] = $this->home_model->get_testimonials($limit = 3);
	
			print_r(json_encode($data));
			return;
		}
		$response['message'] = "You are not logged in !";
		print(json_encode($response));
    }

}

?>
