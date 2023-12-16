#Can think of this as our genome
class Resource:
    #Initialization step. This is base off our simulation metric receive and 
    #not the resource extraction receive 
    def __init__(self, cpu, memory, storage):
        self.cpu = cpu 
        self.memory = memory 
        self.storage = storage
        self.fitness = 0 
    
    @staticmethod
    def crossover():
        pass

    @staticmethod
    def point_mutate():
        pass

    @staticmethod
    def shrink_mutate():
        pass

    @staticmethod
    def grow_mutate():
        pass
