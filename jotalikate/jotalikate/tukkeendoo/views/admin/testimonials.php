<?php include('header.php'); ?>
<?php include('left.php'); ?>

<script type="text/javascript" language="javascript">

var baseurl = "<?php print base_url(); ?>";
function group_ajax(url) {
		
		$.ajax({
		type: "POST",
		url: baseurl+"admin/groups/group_ajax/"+url,		
		success: function(html){		
		var message = $('<div />').append(html).find('#splitresult').html();
		$("#pageresult").html(message);
		$('body').find('.bacis a').popover();
		}
	});
}
function areyousure()
{
	return confirm('<?php echo 'delete_confirm';?>');
}
</script>
<?php echo admin_js('admin.js', true);?>

<div id="content-wrapper">
					<div class="row">
						<div class="col-lg-12">
                                <ol class="breadcrumb">
                                    <li><a href="<?php echo base_url('admin/dashboard'); ?>"><?php echo lang('admin_home'); ?></a></li>
                                    <li class="active"><span><?php echo lang('testimonials');?></span></li>
                                </ol>
                                
                                <div class="clearfix">
                                    <h1 class="pull-left"><?php echo lang('testimonials');?></h1>
                                    
                                    <div class="pull-right top-page-ui">
                                        <a href="<?php echo base_url('admin/testimonials/form');?>" class="btn btn-primary pull-right">
                                            <i class="fa fa-plus-circle fa-lg"></i> <?php echo lang('add_testimonial');?>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
								<div class="col-lg-12">
									<div class="main-box no-header clearfix">
										<div class="main-box-body clearfix">
											<div id="pageresult">
                                            <div class="table-responsive">                                             
												<table class="table user-list table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th><span>Id</span></th>
                                                            <th><span><?php echo lang('testimonials_name');?></span></th>
                                                            <th><span><?php echo lang('testimonials_description');?></span></th>            
                                                            <th><span><?php echo lang('created');?></span></th>
                                                            <th class="text-center"><span><?php echo lang('status');?></span></th>															
                                                            <th>&nbsp;</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?php foreach ($testimonials as $testimonial):?>
                                                        <tr>
                                                            <td>
                                                                <?=$testimonial['id']?>
                                                            </td>
                                                            <td>
                                                                <?=$testimonial['name']?>
                                                            </td>
                                                            <td>
                                                                <?=$testimonial['description']?>
                                                            </td>            
                                                            <td>
                                                                <?php echo date('d, M Y h:i A',strtotime($testimonial['created_date']));?> 
                                                                
                                                            </td>
                                                           <td class="text-center">
                                                            <?php if($testimonial['isactive'] == 0) { ?>
                <span class="label label-default" id="label-<?=$testimonial['id']?>"><?php echo lang('inactive');?></span>
                                                            <?php } else { ?>
                                                             <span class="label label-success" id="label-<?=$testimonial['id']?>"><?php echo lang('active');?></span>
                                                            <?php } ?>
               </td>
                                                            <td class="text-center">
                                                                <div class="btn-group" id="btn-<?=$testimonial['id']?>">
                                                                    <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
                                                                        <?php echo lang('change_status');?> <span class="caret"></span>
                                                                    </button>
                                                                    <ul class="dropdown-menu" role="menu">
                                                                    <?php if($testimonial['isactive'] == 0) { ?>
                                                                        <li style="border:0px; height:auto; padding:0px;"><a href="#" id="enable-<?=$testimonial['id']?>" class="change-status-testimonials" rel="<?=$testimonial['id']?>"><?php echo lang('disable');?></a></li>
                                                                    <?php } else { ?>
                                                                     <li style="border:0px; height:auto; padding:0px;"><a href="#" id="disable-<?=$testimonial['id']?>" class="change-status-testimonials" rel="<?=$testimonial['id']?>"><?php echo lang('enable');?></a></li>
                 <?php } ?>
                                                                        
                                                                        <li class="divider"></li>
                                                                    </ul>
                                                                    
                                                                 </div>
                                                            </td>
                                                            <td style="width: 20%;" class="bacis">																
                                                                <a href="<?php echo base_url('admin/testimonials/form/'.$testimonial['id']);?>" class="table-link">
                                                                    <span class="fa-stack">
                                                                        <i class="fa fa-square fa-stack-2x"></i>
                                                                        <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                                    </span>
                                                                </a>
                                                                <a href="<?php echo base_url('admin/testimonials/delete/'.$testimonial['id']);?>" onclick="return areyousure();" class="table-link danger">
                                            <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                        </span>
            
                                                                </a>
                                                            </td>
                                                        </tr>
                                                        <?php endforeach; ?>
                                                    </tbody>
                                                </table>
											</div>
                                            <?php echo $pagination?>
										</div>
                                        </div>
									</div>
								</div>
							</div>
						
						
					</div>
                     <script>
	 $(document).ready(function() {
       $('body').find('.bacis a').popover();
            });
	</script>


<?php include('footer.php'); ?>
