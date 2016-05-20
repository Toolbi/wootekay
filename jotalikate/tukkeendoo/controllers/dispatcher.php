<?php

class Dispatcher extends Front_Controller {

    function __construct() {
        parent::__construct();

        $this->load->library('Auth_travel');
        $this->load->library('OAuth2');
        $this->lang->load('login');
        $this->load->model('Travel_model');
        $this->load->helper('url');
    }

    function index() {
        //we check if they are logged in, generally this would be done in the constructor, but we want to allow customers to log out still
        //or still be able to either retrieve their password or anything else this controller may be extended to do
        $isLogin = $this->auth_travel->is_logged_in(false, false);
        //if they are logged in, we send them back to the dashboard by default, if they are not logging in
        if ($isLogin) {
        	
        	$page = $this->input->post('page');
        	if (!isset($page)) {
				$page = $this->input->get('page');
			}
			
			$input = $this->input;
			 $this->session->set_flashdata('input',$input);
			if (isset($page)) {
				redirect($page);
			}else{
				redirect('home');
			}
            
        }else{
        	$page = $this->input->get('page');
			if (isset($page) && $page != '') {
				redirect($page);
			}else{
				redirect('login');
			}
        	
        }
    }

}

?>