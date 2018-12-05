import os


# 用于生成Makedown格式的目录
class GenContexts:

    @classmethod
    def generate(cls, folder_path, root_url, parent_path, deep_level, catalog: list):
        files = os.listdir(folder_path)
        for fileName in files:
            file_path = os.path.abspath(folder_path) + os.sep + fileName
            full_name = parent_path + "/" + fileName
            if os.path.isdir(file_path):
                icon = " :books:" if deep_level==0 else " :book:"
                catalog.append(
                    "&nbsp;" * 10 * deep_level + icon + "[" + fileName + "]" + "(" + root_url + full_name + ")" + " </br>\n")
                cls.generate(file_path, root_url, full_name, deep_level + 1, catalog)
            elif os.path.isfile(file_path) and '.md' in fileName:
                catalog.append(
                    "&nbsp;" * 10 * deep_level + " :memo:" + "[" + fileName + "]" + "(" + root_url + full_name + ")" + " </br>\n")


if __name__ == '__main__':
    catalog = []
    GenContexts.generate(os.getcwd() + os.sep + "notes",
                         "https://github.com/heibaiying/LearningNotes/tree/master/notes", "", 0,
                         catalog)
    with open("catalog.md", 'w') as f:
        for line in catalog:
            f.write(line)
