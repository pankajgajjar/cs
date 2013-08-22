package app.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.cs.boundary.Service;

/**
 * The Class ChapterController. TODO. com.cs.business.ifacadeservices controller
 * -> common facade ->>>(|) ->i***interface call ichapter ->Impl idimension
 * ->Impl
 */
@Controller
public class ChapterController {

	/** The Constant CREATE. */
	private static final String CREATECHAPTER = "/chapter/create/{type}/name/{name}/path/{path}/folder/{folder}";
	private static final String CREATEPAGE = "/page/create/{type}/name/{name}/path/{path}/folder/{folder}";
	private static final String MOVECHAPTER = "/chapter/move/{type}/name/{name}/path/{path}/folder/{folder}/newpath/{newpath}";
	private static final String MOVEPAGE = "/page/move/{type}/name/{name}/path/{path}/folder/{folder}/newpath/{newpath}";

	/** The chapter service. */
	private Service chapterService;

	/**
	 * Instantiates a new chapter controller.
	 * 
	 * @param chapterService
	 *            the chapter service
	 * @param factory
	 *            the factory
	 */
	@Autowired
	public ChapterController(Service chapterService) {
		this.chapterService = chapterService;

	}

	/**
	 * Creates the.
	 * 
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param path
	 *            the path
	 * @param isFolder
	 *            the is folder
	 * @return the string
	 */
	@RequestMapping(value = { CREATECHAPTER, CREATEPAGE })
	public @ResponseBody
	String create(@PathVariable("type") String type,
			@PathVariable("name") String name,
			@PathVariable("path") String path,
			@PathVariable("folder") boolean isFolder) {

		return chapterService.create(type, name, path, isFolder);

	}

	/**
	 * Move.
	 * 
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param path
	 *            the path
	 * @param isFolder
	 *            the is folder
	 * @param newPath
	 *            the new path
	 */
	@RequestMapping(value = { MOVECHAPTER, MOVEPAGE })
	public @ResponseBody
	String move(@PathVariable("type") String type,
			@PathVariable("name") String name,
			@PathVariable("path") String path,
			@PathVariable("folder") boolean isFolder,
			@PathVariable("newpath") String newpath) {

		System.out.println("==>" + type + name + path + isFolder + newpath);
		chapterService.move(type, name, path, isFolder, newpath);
		return name;

	}
}
