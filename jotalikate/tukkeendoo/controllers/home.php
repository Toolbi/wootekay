<?php

class Home extends Front_Controller 
{

    function __construct() 
	{
        parent::__construct();
        $this->load->model('home_model');
     
    }

    function index() 
	{
        $this->load->helper('form');
        $this->load->helper('text');
        $data['testimonials'] = $this->home_model->get_testimonials($limit = 3);
        $data['trips'] = $this->home_model->get_recently_trip_list($limit = 10, $data);
//        echo '<pre>';print_r($data);echo'</pre>';exit;

		//print_r($_GET);
        $this->load->view('home', array('data' => $data));
		//redirect('login');
    }

}

?>
