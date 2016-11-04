<?php
class Dashboard extends Traveller_Controller {	
	var $CI;

	function __construct()
	{
		parent::__construct();
		$this->CI =& get_instance();

		$this->load->library('Auth_travel');
		
		
	}
	function index()
	{
			$data['page_title'] = lang('dashboard');
			$carpool_session['carpool_session']		= $this->CI->carpool_session->userdata('carpool');
			$id	= $carpool_session['carpool_session']['user_id'];
			$data['customer'] = $this->Customer_model->get_customer($id);

			$this->load->helper('form');
					
			//$this->load->view('dashboard', $data);
			print_r(json_encode($data)); // TODO test
	}
}