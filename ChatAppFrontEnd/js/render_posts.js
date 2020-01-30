
	var edit_new_post = document.getElementById("edit_new_post");
	
	edit_new_post.addEventListener("tap",function(){
		mui.openWindow("edit_posts.html","edit_posts.html");
	},{passive:false});
	
	mui.plusReady(function(){
		var openMenu = document.getElementById('tog_bg');
		openMenu.addEventListener('tap',function(){
			mui('#sheet_background_img').popover('toggle');
		}, { passive: false });
	});
	
	

	var chooseImg = document.getElementById('choose_background_img');
	var downloadImg =  document.getElementById('save_background_img');
	
	/**
	 * Choose Images from client phone 
	 */
	chooseImg.addEventListener('tap',function(){
		console.log('chooseImg');
		mui.openWindow({
			url: '../plugin/v3.1.6/myface-uploader.html',
			id: 'myface-uploader.html',
			extras: {  
            	type_name: 'Background Img'  
        	},  
			createNew: true});
		mui('#sheet_background_img').popover('toggle');
	}, { passive: false })
	
	/**
	 * download moment background img to gallery.
	 */
	downloadImg.addEventListener('tap',function(){
			console.log('saveImg');
			
			plus.nativeUI.showWaiting("Downloading");
			
			var user =app.getUserGlobalInfo();
			var background_img = user.momentBackgroundImg; 
			var imgURL;
			if(background_img != null){
				imgURL = app.imgServerUrl + background_img;
			}else{
				imgURL = "../image/bg.png";
			}
			
			/**
			 * Download the imgs through http protocal, 
			 * and save the temp file to client phone
			 */
			var imgDownloadTask = plus.downloader.createDownload(imgURL,{}, function(downloadFile, status){
				plus.nativeUI.closeWaiting();
				if(status == 200){
					var tempFile = downloadFile.filename;
					plus.gallery.save(tempFile,function(){
						app.showToast("Save Image Success", "success");
						mui('#sheet_background_img').popover('toggle');
					})
					
				}else{
					console.log("error")
					app.showToast("Download image failed","error")
				}
			})
			
			imgDownloadTask.start();
			
		}, { passive: false })
	

	
	
	function renderList(data){
		// update count;
		count = data.count;
		var table = document.body.querySelector('.ultable');
		for(var idx = 0 ; idx < count; idx ++){
			curIndex++;
			ul_html = renderPost(data.posts[idx], data.owners[idx]);
			table.innerHTML += ul_html; 
		}
		
	}
	
	function renderPost(post, owner){
		
		var owner_img_src = owner.friendImageBig;
		var owner_nick_name = owner.friendNickname;
		var post_content = post.postContent;
		
		
		var li_html = '<li>' + 
							'<div class="body">' +
								'<div class="logo">' + 
									'<img src="'+owner_img_src +'">' + 
								'</div>' +
								'<div class="text">'+
									'<div class="title">'+ owner_nick_name +'</div>' +
										renderPostContent(post_content,post.postId) +
										renderPostImg(post.postImgList) +
										renderPostTime(post.postCreateTime)+
										
								'</div>' + 
							'</div>' +
						'</li>';
		return li_html;
	}
	
	function renderPostContent(post_content,id){
		var post_content_short = null;
		var post_content_more = null; 
		if(post_content.length > 30){
			post_content_short = post_content.substring(0,30);
			post_content_more =   post_content.substring(30);
		}
		var html = '<div class="txt">'; 
		if(post_content_short != null){
			html += post_content_short ;
			html += '<span id="p'+id+'">...</span>' + 
					'<span id="txt'+id+'" style="display:none;">' +
						post_content_more +
					'</span>' + 
					'</div>' + 
					'<div style="color: #88B1C5;" onclick="more(this,"'+id+'")">More</div>'
			console.log(html);
		}else{
			html += post_content + '</div>';
		}
		
		return html;
	}
	
	function renderPostImg(postImgList){
		var html = '<div class="my-gallery" data-pswp-uid="'+ curIndex +'">' +
		var img_server = app.imgServerUrl;
		for(var idx = 0 ; idx < postImgList.length; idx++){
			var img_src = postImgList[idx].postImg;
			
			if( img_src != null && img_src.length != 0){
				img_src = app.imgServerUrl + faceImageBig;
			}else{
				img_src = "../css/registLogin/default_img.jpg";
			}
			
			var figure_html = '<figure>' +
									'<div>'+
										'<a href="'+img_src+'">'+
											'<img style="width:100%;">'+
									  	'</a>'+
									'</div>'+
							   '</figure>'
							   
			html += figure_html;
		}
		
		html += '</div>';	
		
		return html;		
	}
	
	function renderPostTime(postTime){
		var time = app.getDateDiff(postTime);
		var html = '<div class="tm">'+
						'<div class="fl">'+ time + '</div>'
					'</div>'
					
		return html; 
		
	}