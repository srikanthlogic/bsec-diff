<?php

$versions = json_decode(file_get_contents('version.json'), true);

foreach($versions as $data) {
	$zip = $data['source'];
	// Unzip, overwrite, and extract to src/
	`unzip -o $zip -d "src/"`;

	$timestamp = $data['releasetime'];
	$version = $data['version'];
	`git tag -d v$version`;
	`git add src/`;	
	`GIT_COMMITTER_DATE="$timestamp" git commit --author "PhoneMe <?@myphoneme.com>" --message "Version $version"`;
	`git tag v$version`;
	if ($version != 'Phase11v0nochange') {
		// In case some files are deleted, they should get picked up as well
		`rm -rf src/`;
	}
}