/**
 * Upload Controller
 */

var UploadController = function($scope, $fileUploader, $timeout) {
  'use strict';

  // create a uploader with options
  var uploader = $scope.uploader = $fileUploader.create({
    scope : $scope, // to automatically update the html. Default: $rootScope
    url : '/boardMap/upload',
    filters : [ function(item) { // first user filter
      return item.name.endsWith(".csv")
    } ]
  });

  $scope.$watch('boardMap', function(newVal, oldVal) {
    if (newVal != null) {
      console.log('newVal.id: ' + newVal.id)
      uploader.formData = [ {
        id : $scope.boardMap.id
      } ]
    }

  });

  // REGISTER HANDLERS

  uploader.bind('afteraddingfile', function(event, item) {
    console.info('After adding a file', item);
    $scope.showUploadDetails = true
  });

  uploader.bind('whenaddingfilefailed', function(event, item) {
    console.info('When adding a file failed', item);
    $scope.showUploadDetails = true
  });

  uploader.bind('afteraddingall', function(event, items) {
    console.info('After adding all files', items);
    $scope.showUploadDetails = true
  });

  uploader.bind('beforeupload', function(event, item) {
    console.info('Before upload - for boardMap: ' + $scope.boardMap.id, item);
    uploader.formData = [ {
      id : $scope.boardMap.id
    } ]
  });

  uploader.bind('progress', function(event, item, progress) {
    console.info('Progress: ' + progress, item);
  });

  uploader.bind('success', function(event, xhr, item, response) {
    console.info('Success', xhr, item, response);
  });

  uploader.bind('cancel', function(event, xhr, item) {
    console.info('Cancel', xhr, item);
  });

  uploader.bind('error', function(event, xhr, item, response) {
    console.info('Error', xhr, item, response);
  });

  uploader.bind('complete', function(event, xhr, item, response) {
    console.info('Complete', xhr, item, response);
  });

  uploader.bind('progressall', function(event, progress) {
    console.info('Total progress: ' + progress);
  });

  uploader.bind('completeall', function(event, items) {
    console.info('Complete all', items);
  });

  $scope.uploadClick = function() {
    $scope.showUploadDetails = true
    $('#uploadInput').click()
  }
}