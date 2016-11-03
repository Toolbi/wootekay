<div id="splitresult">
<div class="table-responsive">                                             
<table class="table user-list table-hover">
<thead>
    <tr>
        <th><span><?php echo lang('first_name');?></span></th>
        <th><span><?php echo lang('last_name');?></span></th>
        <th><span><?php echo lang('telephone');?></span></th>
        <th><span><?php echo lang('email');?></span></th>
        <th class="text-center"><span><?php echo lang('status');?></span></th>															
        <th>&nbsp;</th>
    </tr>
</thead>
<tbody>
<?php foreach ($traveller as $travel):?>
    <tr>
        <td>
            <?=$travel['user_first_name']?>
        </td>
        <td>
            <?=$travel['user_last_name']?>
        </td>
        <td>
            <?=$travel['user_mobile'];?> 
            
        </td>
        <td>
            <?=$travel['user_email']?>
        </td>
       <td class="text-center">
                                                            <?php if($travel['isactive'] == 0) { ?>
                <span class="label label-default" id="label-<?=$travel['user_id']?>"><?php echo lang('inactive');?></span>
                                                            <?php } else { ?>
                                                             <span class="label label-success" id="label-<?=$travel['user_id']?>"><?php echo lang('active');?></span>
                                                            <?php } ?>
               </td>
                                                            <td class="text-center">
                                                                <div class="btn-group" id="btn-<?=$travel['user_id']?>">
                                                                    <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
                                                                        <?php echo lang('change_status');?> <span class="caret"></span>
                                                                    </button>
                                                                    <ul class="dropdown-menu" role="menu">
                                                                    <?php if($travel['isactive'] == 0) { ?>
                                                                        <li style="border:0px; height:auto; padding:0px;"><a href="#" id="enable-<?=$travel['user_id']?>" class="change-status-traveller" rel="<?=$travel['user_id']?>"><?php echo lang('enable');?></a></li>
                                                                    <?php } else { ?>
                                                                     <li style="border:0px; height:auto; padding:0px;"><a href="#" id="disable-<?=$travel['user_id']?>" class="change-status-traveller" rel="<?=$travel['user_id']?>"><?php echo lang('disable');?></a></li>
                 <?php } ?>
                                                                        
                                                                        <li class="divider"></li>
                                                                    </ul>
                                                                    
                                                                 </div>
                                                            </td>
        <td style="width: 20%;" class="bacis">																
           <a href="<?php echo base_url('admin/traveller/form/'.$travel['user_id']);?>" class="table-link">
                <span class="fa-stack">
                    <i class="fa fa-square fa-stack-2x"></i>
                    <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                </span>
            </a>
           <a href="<?php echo base_url('admin/traveller/delete/'.$travel['user_id']);?>"  onclick="return areyousure();" class="table-link danger">
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