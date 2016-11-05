<?php

class Home extends Front_Controller 
{

    function __construct() 
	{
        parent::__construct();
        $this->load->model('home_model');
     	$this->load->library('Auth_travel');
    }

    function index() 
	{
<<<<<<< HEAD
		$isLogin = $this->auth_travel->is_logged_in(false, false);
		if ($isLogin) {
			$this->load->helper('form');
	        $this->load->helper('text');
	        $data['testimonials'] = $this->home_model->get_testimonials($limit = 3);
	        $data['trips'] = $this->home_model->get_recently_trip_list($limit = 10, $data);
			//echo '<pre>';print_r($data);echo'</pre>';exit;
	
			print_r(json_encode($data));
	        //$this->load->view('home', array('data' => $data));
			//redirect('login');
			return;
		}
		$response['message'] = "You are not logged in !";
		print(json_encode($response));
=======
<<<<<<< HEAD
		if($this->auth_travel->is_logged_in(false, false)){  
			print_r(json_encode($profile));
		 
	        $this->load->helper('form');
	        $this->load->helper('text');
	        $data['testimonials'] = $this->Home_model->get_testimonials($limit = 3);
	        $data['trips'] = $this->Home_model->get_recently_trip_list($limit = 10, $data);
			
			print_r(json_encode($data)); // TODO test
			
	//        echo '<pre>';print_r($data);echo'</pre>';exit;
	
			//print_r($_GET);
	        //$this->load->view('home', array('data' => $data));
			//redirect('login');
		};
=======
        $this->load->helper('form');
        $this->load->helper('text');
        $data['testimonials'] = $this->Home_model->get_testimonials($limit = 3);
        $data = $this->Home_model->get_recently_trip_list($limit = 10, $data);
//        echo '<pre>';print_r($data);echo'</pre>';exit;

        $this->load->view('home', $data);
		//redirect('login');
>>>>>>> master
>>>>>>> master
    }

}

?>
