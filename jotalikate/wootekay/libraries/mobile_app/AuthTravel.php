<?php

/**
 * 
 */
class AuthTrvel extends Auth_travel {
	
	function __construct() {
		parent::__construct();
	}
	
	/*
	this function does the logging in.
	*/
	function login($phone, $password, $remember=false)
	{
		$this->CI->db->select('*');
		$this->CI->db->where('user_mobile', $phone);
		$this->CI->db->where('user_password',  sha1($password));
		$this->CI->db->where('isactive',  1);
		
		$this->CI->db->limit(1);
		$result = $this->CI->db->get('tbl_users');
		
		$result	= $result->row_array();
		
		if (sizeof($result) > 0)
		{
			if($result['user_admin_status'] == 0 ){
				$this->CI->session->set_flashdata('error','Your account is disabled, please contact '.$this->CI->config->item('admin_email'));
				redirect('login');
			}
			$save['user_id'] =  $result['user_id'];
			$save['user_lost_login'] = date('Y-m-d H:i:s',now());
			
			$this->save($save);
			$carpool = array();
			$carpool['carpool']			= array();
			$carpool['carpool']['user_email']		= $result['user_email'];
			$carpool['carpool']['access'] 	= 'travel';			
			$carpool['carpool']['user_id']	= $result['user_id'];
			$carpool['carpool']['trip_id']	= $this->trips($result['user_id']);
		
			if(!$remember)
			{
				
				$carpool['carpool']['expire'] = time()+$this->session_expire;
			}
			else
			{
				
				$carpool['carpool']['expire'] = false;
			}

			$this->CI->carpool_session->set_userdata($carpool);
			$data['login_ip']=$this->CI->input->ip_address();
			$data['login_id']=$result['user_id'];					
			$id=$this->savelog($data);
		
			return true;
		}
		else
		{
			return false;
		}
	}
}
