<?php

class Register extends Front_Controller 
{

    function __construct() 
	{
        parent::__construct();

        $this->load->helper('url');
    }

    function index() 
	{

        //we check if they are logged in, generally this would be done in the constructor, but we want to allow customers to log out still
        //or still be able to either retrieve their password or anything else this controller may be extended to do
        $redirect = $this->auth_travel->is_logged_in(false, false);
        //if they are logged in, we send them back to the dashboard by default, if they are not logging in    
        if ($redirect) 
		{
            redirect('login');
        }else if (isset($_POST) && count($_POST) > 4) {
            
	        $this->load->helper('form');
	
	        $this->load->library('form_validation');
	        $this->form_validation->set_error_delimiters('<div>', '</div>');
	
	        /*
	          we're going to set this up early.
	          we can set a redirect on this, if a customer is checking out, they need an account.
	          this will allow them to register and then complete their checkout, or it will allow them
	          to register at anytime and by default, redirect them to the homepage.
	         */
	        //$data['redirect'] = $this->session->flashdata('redirect');
	
	        $data['seo_title'] = 'Register';
	        $data['seo_description'] = '';
	        $data['seo_keyword'] = '';
	
	
	        //default values are empty if the customer is new
	
	        $data['first_name'] = '';
	        $data['last_name'] = '';
	        $data['password'] = '';
	        $data['email'] = '';
	        $data['phone'] = '';
	        //$data['txttype'] = '';
	
	
	        $this->form_validation->set_rules('first_name', 'Firstname', 'trim|max_length[128]');
	        $this->form_validation->set_rules('last_name', 'Lastname', 'trim|max_length[128]');
	        $this->form_validation->set_rules('email', 'Email', 'trim|required|valid_email|max_length[128]|callback_check_email');
	        $this->form_validation->set_rules('phone', 'Phone', 'trim|required|max_length[32]');
	        $this->form_validation->set_rules('password', 'Password', 'required|min_length[6]');
	
	
	        if ($this->form_validation->run() == FALSE) 
			{
	            //if they have submitted the form already and it has returned with errors, reset the redirect
	            if ($this->input->post('submitted')) 
				{
	                $data['redirect'] = $this->input->post('redirect');
	            }
	
	            // load other page content 
	
	            $this->load->helper('directory');
	
	            $data['first_name'] = $this->input->post('first_name');
	            $data['last_name'] = $this->input->post('last_name');
	            //$data['type'] = $this->input->post('type');
	            $data['email'] = $this->input->post('email');
	            $data['phone'] = $this->input->post('phone');
	
	
	
	            $data['error'] = validation_errors();
	
	
	            //$this->load->view('register', $data);
				
				$response['message'] = lang('error_register_failed');
				$response['success'] = FALSE;
	        } 
			else 
			{
	            $data['seo_title'] = 'Register';
	            $data['seo_description'] = '';
	            $data['seo_keyword'] = '';
	
	            $save['user_id'] = false;
	            $save['user_first_name'] = $this->input->post('first_name');
	            $save['user_last_name'] = $this->input->post('last_name');
	            $save['user_mobile'] = $this->input->post('phone');
	            $save['user_email'] = $this->input->post('email');
	            $code = random_string('alnum', 6);
	            $save['isverified'] = sha1($code);
	            $save['isactive'] = 0;
	            $save['login_type'] = 0;
	            $save['user_password'] = sha1($this->input->post('password'));
	
	/*
	            $redirect = $this->input->post('redirect');
	
	            //if we don't have a value for redirect
	            if ($redirect == '') 
				{
	                $redirect = 'login';
	            }
	 */
	
	            // save the customer info and get their new id
	            $id = $this->Travel_model->save($save);
	
	///*			// send an email */
	////			// get the email template
	            $res = $this->db->where('tplid', '3')->get('tbl_email_template');
	            $row = $res->row_array();
	
	            // set replacement values for subject & body
	            // {customer_name}
	            
	            $row['tplsubject'] =  str_replace('{COMPANY_NAME}', $this->config->item('company_name') . ' ', $row['tplsubject']);
	            
	            $row['tplmessage'] = str_replace('{NAME}', $this->input->post('first_name') . '.' . $this->input->post('last_name'), $row['tplmessage']);
	
	            $row['tplmessage'] = str_replace('{EMAIL}', $this->input->post('email'), $row['tplmessage']);
	
	            $row['tplmessage'] = str_replace('{IP}', $this->input->ip_address(), $row['tplmessage']);
				
		    	$row['tplmessage'] = str_replace('{COMPANY_NAME}', $this->config->item('company_name'), $row['tplmessage']);
	    
	            $row['tplmessage'] = str_replace('{ADMIN_EMAIL}', $this->config->item('admin_email'), $row['tplmessage']);
				
	
	
	
	            // {url}
	            $test = $this->config->item('test');
	            $url = str_replace('testmobile.', $test ? 'testdev.' : '', $this->config->item('base_url'));
	            $url = str_replace('mobile.', $test ? 'testdev.' : '', $url);
				
	            $row['tplmessage'] = str_replace('{SITE_PATH}', $url, $row['tplmessage']);
	
	            $row['tplmessage'] = str_replace('{code}', $code, $row['tplmessage']);
	
	
	            $param['message'] = $row['tplmessage'];
	
	            $email_subject = $this->load->view('template', $param, TRUE);
				
				//print_r($email_subject);
				//return;
	
	            $this->load->library('email');
	
	            $config['mailtype'] = 'html';
	
	            $this->email->initialize($config);
	
	            $this->email->from($this->config->item('email'), $this->config->item('company_name'));
	            $this->email->to($save['user_email']);
	            $this->email->bcc($this->config->item('email'));
	            $this->email->subject($row['tplsubject']);
	            $this->email->message(html_entity_decode($email_subject));
	
				//print_r($this->email);
				
	            $this->email->send();
	
	            //$this->session->set_flashdata('message', 'Thanks for registering with '. $this->config->item('company_name').'! verification email is sent.');
	            //redirect($redirect);
	            $response['message'] = 'Thanks for registering with '. $this->config->item('company_name').'! verification email is sent.';
				$response['success'] = TRUE;
		
	        }
			
	        print_r(json_encode($response));
        }else{
        	redirect('dispatcher');
        }
    }

    function check_email($str) 
	{
        $email = $this->auth_travel->check_email($str);
        if ($email) 
		{
            $this->form_validation->set_message('check_email', lang('error_email'));
            return FALSE;
        } 
		else 
		{
            return TRUE;
        }
    }

}
